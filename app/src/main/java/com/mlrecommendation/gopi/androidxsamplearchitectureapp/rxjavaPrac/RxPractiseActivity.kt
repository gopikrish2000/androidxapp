package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit
import java.lang.Thread.sleep as sleep1
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.delay
import java.util.concurrent.Executors
import io.reactivex.internal.operators.single.SingleInternalHelper.toObservable



@SuppressLint("CheckResult")
class RxPractiseActivity : AppCompatActivity() {

    /*

        doubts
       . differ vs observable.create ( creates fresh observable what it means)


    * Things to consider in Rxjava
    * 1. creator function when it is being called ( when calling getObservable or upon subscription)
    * 2. creator function how many times its being called ( upon every subscription or once {hot , cold , semicold ...}
    * 3. After creation function is it auto completing or it can emit multiple items.
    *
    *************************************************************Points from video https://www.youtube.com/watch?v=QdmkXL7XikQ  common mistakes in RxJava ***********************

. FlatMap ( unordered merge) -> make new observable for each item & then flattens all observables to get in a single stream with single subscribe { while flattening order can be unordered like 1 produces [1, a] ; 2 produces [2,b] in flatmap } then order can be 1,a,2,3,b,c  { can be interleaved...}
concatMap ( perfect order of merge)  - output will be 1,a,2,b,3,c ( perfect ordering)

. Avoid using observable.create() as much as possible. Also when using it always use it.onComplete() to terminate it.
. Try to use Observable.fromCallable() or Observable.from(list...) than create() method
. toList() will work only when onComplete is called like observable.create( forloop ... {it.onNext(1)}).toList().subscribe{print list} // does nothing as onComplete is not called.
. List<String> conveert to List<Student>  one string can give multiple students..
. Dont reuse existing subscribition for 2 times like observable.subscribe(subscribition); observable.subscribe(subscribition)
. subscribeOn() { works on Upstream [all statements above it]} write only ONCE in Stream {as it takes the first subscribeOn thatsit } but ObservableOn(){ works on Downstream [ all statements below it]} u can write multiple times it works completely fine
. Do this for Cold Observables which are expensive like Network calls ... -> publish() & connect() manually use Autoconnect() -> which when first susbscriber register it executes but when that unregisters ; it still continues like hot subscriber ( just difference is it starts only when first subscription is done. { hot subscriber can start without single subscribition}) -> So thiis is kind of Warm observable.
. Use publish().autoConnect()  instead of publish().refCount()
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.layout.activity_rx_practise)

//        basicRxtest()
//        advancedRxJava()

//        advancedRxJava1()
//        advancedRxJava2()
//          typesOfObservables()
        advancedRxJava3()

//        advancedRxJava4()
    }

    fun advancedRxJava5(){
        /*val memory = dataSource.getDataFromMemory()
        val disk = dataSource.getDataFromDisk()
        val network = dataSource.getDataFromNetwork()

        Observable.concat(memory, disk, network)
            .firstElement()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .subscribe()*/
    }

    private fun advancedRxJava4() {  // In Single chain multiple subscribeOn() takes first statement of it . But if u r return new Observable inside flatMap.. then if u subscribeOn for this new Observable then it will work in different Thread.
       // RxCachedThreadScheduler-1 is IO Thread.
        // RxComputationThreadPool-2 is computational Thread.

        // Output Create1 RxCachedThreadScheduler-1 ; FlatMap1 RxCachedThreadScheduler-1 => same thread as In same Observable chain if u write multiple subscribeOn it takes first one only => Here io thread.
        Observable.create<String> { emitter ->
            println("Create1 " + Thread.currentThread().getName()) // same io thread
            emitter.onNext("1")
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
            .flatMap { Observable.fromCallable { it.substring(1); println(" FlatMap1 "+ Thread.currentThread().getName()) } } // same io thread
            .subscribeOn(Schedulers.computation()).subscribe { println(" subscribe "+ Thread.currentThread().getName()) } // same io thread

        Observable.create<String> { emitter ->
            println("Create1 " + Thread.currentThread().getName())  // prints main here
            emitter.onNext("1")
            emitter.onComplete()
        }.observeOn(Schedulers.io())
            .flatMap { Observable.fromCallable { it.substring(1); println(" FlatMap1 "+ Thread.currentThread().getName()) } }  // io threead
            .observeOn(Schedulers.computation()).subscribe { println(" subscribe "+ Thread.currentThread().getName()) }  // computation thread.

        // Output of this Create2 RxCachedThreadScheduler-1  ;; FlatMap2 RxComputationThreadPool-1 => different threads as different subscribeOn in the nested new Observable.
        Observable.create<String> { emitter ->
            println("Create2 " + Thread.currentThread().getName()) // io thread
            emitter.onNext("1")
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
            .flatMap { Observable.fromCallable { it.substring(1); println(" FlatMap2 "+ Thread.currentThread().getName()) }.subscribeOn(Schedulers.computation()) } // computation thread.
        .subscribe {  }
    }

    private fun typesOfObservables() {
        /*Observable
        Flowable - when u have many items which Observable will ignore. Here u can apply backpressure strategy, otherwise get MissingBackPressureException
        Single - Returns only 1 value like Networkcall response
        Maybe - May return 1 value or return nothing.
        Completable - wont return a value will just perform task.
        */
        Observable.create<String> { emitter ->  emitter.onNext("1");emitter.onComplete()
        }.subscribeOn(Schedulers.computation()).subscribe { println("Observable val $it") }
        Flowable.create(FlowableOnSubscribe<Int> { it.onNext(2); it.onComplete() }, BackpressureStrategy.DROP).subscribe { println ("flowable val $it") }
        Single.create<String>(SingleOnSubscribe { /*it.onSuccess("singleData");*/ it.onError(RuntimeException("MyException")) }).subscribe { t1, t2 -> println("single val $t1 $t2") }
        Maybe.create<String>(MaybeOnSubscribe { it.onSuccess("maybeData"); it.onComplete() }).subscribe ({ t1-> println(" maybe val $t1") }){error -> println(" error $error")}
        Completable.create(CompletableOnSubscribe { for (i in 1..10) { } }).subscribe { println(" completable task done ") }

        val publishSubject = PublishSubject.create<Int>()
        publishSubject.onNext(2)

        publishSubject.subscribe { println(" publishSubject val $it") }

    }

    private fun advancedRxJava2() {
        val observable = Observable.create<Int>{
            for (i in 1..100) {
                it.onNext(i);
                Thread.sleep(5)
            }
            it.onComplete()
//            sleep1(10000)
        }
//        observable.buffer(observable.debounce(3, TimeUnit.MILLISECONDS)).subscribe { println(" list val $it") }
//        (observable.sample(15, TimeUnit.MILLISECONDS)).subscribe { println(" list val $it") }  // sample vs debounce is Sample will provide most recent new element produced in that time period(no duplicates). Debounce will produce element only when in that time period if a event occured then it shows the last occurance of it
//        (observable.sample(15, TimeUnit.MILLISECONDS)).toList().toObservable().subscribe { println(" list val $it") }
        (observable.sample(8, TimeUnit.MILLISECONDS)).toList().subscribe {t1, t2 ->  println(" list val $t1") } // toList() works only when it.onComplete() is called.
//        val list = (observable.sample(8, TimeUnit.MILLISECONDS)).toList().blockingGet();  println(" list val $list")

    }


    private fun advancedRxJava1() {
        // Reduce functions.
        val sum = Observable.just(1, 2, 3, 4, 5, 6).reduce(0, BiFunction { a, b -> a + b })
            .blockingGet()  // a+b to get sum ; a+1 to get count; Math.max(a,b) for max
        val average =
            Observable.just(1, 2, 3, 4, 5, 6).reduce(Pair(0, 0), BiFunction { a, b -> Pair(a.first + 1, a.second + b) })
                .map { it.second / it.first.toFloat() }.blockingGet()  // for average.
        println("average is $average")

        // Difference btw FlatMap & SwitchMap is SwitchMap can miss few items in conversion if next item can before completion of current item{ those events r missed/ignored } FlatMap gives all items may miss on order of items in this case.

        // Blocking Observables (to) -> used to convert Observable to dataStructures like list,set & other object types like flowable...
        val toList = Observable.range(1, 6).toList()
            .blockingGet() //toSet, toFlowable gives Single<List> u can use blockingGet()... to get Direct DataStructure.
        val toList1 = Observable.range(1, 6).blockingIterable() //toSet, toFlowable, blockingGet...
    }

    private fun advancedRxJava3() {
        // only when connect is called the subscribers will receive events( even when subscribed as well ) & all subscribers gets same value ( Hot Subscriber )
        val observable = Observable.interval(100, TimeUnit.MILLISECONDS).doOnNext { println("event $it") }
        val connectableObservable = observable.publish().autoConnect(2, Consumer { println("disposable object $it") })  // will convert Cold observable to Hot Observable. // use autoConnect with min subscribers
        val firstSubscribe = connectableObservable.subscribe { println("firstSubscriber $it") }
        val secondSubscribe = connectableObservable.subscribe { println("secondSubscriber $it") }
        var thirdSubscribe: Disposable? = null

        println("firstSubscribe is $firstSubscribe ; secondSubscribe is $secondSubscribe")
        Observable.just(1).delay(3, TimeUnit.SECONDS)
            .subscribe { thirdSubscribe = connectableObservable.subscribe { println("thirdSubscriber $it") } }

        Observable.just(1).delay(2, TimeUnit.SECONDS).subscribe { if(secondSubscribe.isDisposed.not()) secondSubscribe.dispose() }

        Observable.just(1).delay(4, TimeUnit.SECONDS).subscribe {  thirdSubscribe?.dispose() ; firstSubscribe.dispose(); }

        /*connectableObservable.refCount().map { it > 2 }
            .subscribe { connectableObservable.connect() }  // doubt why its printing this way */

        //Observable<List<Integer>> burstyBuffered = burstyMulticast.buffer(burstyDebounced);  why we need buffer with closing selector, why cant i directly use debbound

        /*
        * // we have to multicast the original bursty Observable so we can use it
// both as our source and as the source for our buffer closing selector:
Observable<Integer> burstyMulticast = bursty.publish().refCount();
// burstyDebounced will be our buffer closing selector:
Observable<Integer> burstyDebounced = burstyMulticast.debounce(10, TimeUnit.MILLISECONDS);
// and this, finally, is the Observable of buffers we're interested in:
Observable<List<Integer>> burstyBuffered = burstyMulticast.buffer(burstyDebounced);
        * */
    }

    fun advancedRxJava(){
        // convert a list to individual items n emit.
        Observable.fromCallable { return@fromCallable mutableListOf("a","b","c","d","e") }.flatMap { Observable.fromIterable(it) }.subscribe { println("Callable $it") }
        Observable.fromCallable { return@fromCallable mutableListOf("a","b","c","d","e") }.flatMap { list -> Observable.create<String> { subscriber -> list.forEach { subscriber.onNext(it)} } }.subscribe { println("Callable $it") }  // 2nd way. convert a list to individual items n emit.

        // Group Convert individual items to list
        Observable.range(1, 6).toList().map { it.size }.subscribe { t1, t2 -> println("t1")}  // convert individual items toList....

        // Group individual items into windows of size 3 & skips one => prints [a,b,c] , [b,c,d], [c,d,e] { skips one item n print upto count }; If just count=3 without skip => print unique sequences [a,b,c] [d,e]..
        Observable.create<String>{ it.onNext("a"); sleep1(100); it.onNext("b"); sleep1(100); it.onNext("c"); sleep1(100); it.onNext("d"); sleep1(100); it.onNext("e")}.buffer(3,1).subscribe{ println("just item $it")}

        Observable.create<String>{ it.onNext("a"); sleep1(100); it.onNext("b"); sleep1(100); it.onNext("c"); sleep1(100); it.onNext("d");sleep1(100); it.onNext("e"); it.onComplete()}.buffer(80, TimeUnit.MILLISECONDS).subscribe{ println("time specific item $it")}  // Time specific item prints Empty items if no items in that time frame { which is not required generally }

        Observable.create<String>{ it.onNext("a"); it.onNext("a1"); it.onNext("a2");sleep1(100); it.onNext("b"); sleep1(100); it.onNext("c"); sleep1(100); it.onNext("d");sleep1(100); it.onNext("e"); it.onComplete()}.throttleFirst(80, TimeUnit.MILLISECONDS).subscribe{ println("Debounce n other time specific item $it")}  // (Debounce/throttleWithTimeout) picks one element which is last in sequence in given time span of 80 millies { here it prints a2, b,c,d,e } . throttleFirst is exact opposite picks first element in sequence.{a,b,c,d,e}

        println(" Accumulator functions ")
        // Accumulator functions like scan => result of first as input for 2nd element ...
        Observable.just(1, 2, 3, 4, 5).scan { oldVal: Int, currentVal: Int -> oldVal + currentVal }.subscribe { print(" print accumulated sum upto this element $it") }.also { println("") }
//        Observable.just(1, 2, 3, 4, 5).scan { oldVal: Int, currentVal: Int -> oldVal + currentVal }.th.subscribe { println(" scanned results $it") }


        //*** Take only few elements in stream from start or last.
        //Observable.just(1, 2, 3, 4, 5, 6, 7, 8).take(4) // take is take first 4 elements; takeLast(n) = last 4 elements ; skip(n) skip n elements from start...

        // Merge multiple Observables
        val odds = Observable.create<Int>{it.onNext(1); sleep1(100); it.onNext(3); sleep1(100); it.onNext(5); it.onComplete()}.subscribeOn(Schedulers.computation())
        val evens = Observable.create<Int>{it.onNext(2); sleep1(100); it.onNext(4); sleep1(100); it.onNext(6); it.onComplete()}.subscribeOn(Schedulers.computation())
        Observable.merge(odds, evens).subscribe { println("Merge $it") }  // will work when both observable in newThread schedulers ; otherwise it will complete odds first then even {1,3,5,2,4,6} Like Concat
        sleep1(3000)
        odds.mergeWith( evens ).subscribe { println("MergeWith $it") } // 2nd way of merging with Interleaving.

        // Zips 2 items ( use both observable in newThread schedulers for parallel execution) . onErrorResumeNext for starting a new Observable on error.
        Observable.zip<Int,Int,Pair<Int,Int>>(odds,evens, BiFunction {a,b -> Pair(a,b)}).onErrorResumeNext(Observable.just(Pair(1,2))).subscribe { println("Zip is $it") }

        Observable.amb(mutableListOf(odds, evens)).subscribe { println("AMB is $it") } // first observer who responds first will give results only from that observer ( leaves 2nd one ) {1,3,5} here
    }

    private fun basicRxtest() {

        // if u want list with index, just keep a variable outside for index & increment at ur wish & u can do anything
        val list = mutableListOf(1, 2, 3, 4, 5)
        var i = 1;
        Observable.fromIterable(mutableListOf(1, 2, 3, 4, 5)).map { it * i++  }.subscribe { println("value is $it") }
        // 2nd way  of acheving index by using zip & range operator.
        Observable.zip(Observable.fromIterable(list), Observable.range(0,list.size), BiFunction<Int,Int,Pair<Int,Int>> { element, index -> Pair(element,index) }).map { it.first * it.second  }.subscribe { println("value is $it") }


        val obj = RxPracFirstClass()
        val justObservable = obj.getJustObservable()
        val createObservable = obj.getCreateObservable()
        val callableObservable = obj.getCallableObservable()
        val intervalObservable = obj.getIntervalObservable()
        obj.value = "33"
        justObservable.subscribe { println("RxSubscribed JUST Observable value is $it") }
        obj.value += "a"
        justObservable.subscribe { println("RxSubscribed JUST2 Observable value is $it") }

        obj.value = "33callable"
        callableObservable.subscribe { println("RxSubscribed FROM_CALLABLE Observable value is $it") }
        obj.value = "33callable2"
        callableObservable.subscribe { println("RxSubscribed FROM_CALLABLE2 Observable value is $it") }

        obj.value = "33create"
        createObservable.subscribe { println("RxSubscribed CREATE Observable value is $it") }
        obj.value = "33create2"
        createObservable.subscribe { println("RxSubscribed CREATE2 Observable value is $it") }
        obj.value = "33create3"
        createObservable.subscribe { println("RxSubscribed CREATE3 Observable value is $it") }


        intervalObservable.subscribe { println("RxSubscribed INTERVAL Observable value is $it") }
        sleep1(3000)
        intervalObservable.subscribe { println("RxSubscribed INTERVAL2 Observable value is $it") } // order will be different for 1st subscriber & second.
    }


    class RxPracFirstClass(var value:String = "default") {

       fun getJustObservable():Observable<String> {
           println("Called getJustObservable")
           return Observable.just(value)  // return value of instance variable
       }

       fun getCreateObservable():Observable<String> {
           return Observable.create { println("Called getCreateObservable CREATED");it.onNext(value); it.onComplete() }
       }

       fun getCallableObservable():Observable<String> {
           return Observable.fromCallable { println("Called getCallableObservable INSIDE"); return@fromCallable value }
       }

       fun getIntervalObservable():Observable<String> {
           return Observable.interval(1, TimeUnit.SECONDS).map { it.toString() }
       }


   }

}
