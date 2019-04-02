package main.basic;

import java.nio.channels.CompletionHandler;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * #그래서,스레드가뭔가요?
 * 하나의 스레드는 하나의 [코드실행흐름]을 가집니다.
 * 스레드가 2개라면 2개의 코드실행흐름을 가지게 될 수 있습니다.
 * 이와 같이 하나의 프로세스에서 작업을 병렬적으로 처리 할수 있도록 하는 것이 스레드입니다.
 * <p>
 * => 멀티스레드 앱에서는 실행중인 스레드가 한개라도 있다면 프로세스는 종료되지 않습니다.
 * <p>
 * #메인스레드
 * 모든 자바 애플리케이션은 메인 스레드가 main()을 실행하면서 시작됩니다.
 * 메인스레드는 필요에 따라 작업스레드를 만들어서 병렬로 코드를 실행 시킬 수 있습니다.
 * <p>
 * #스레드우선순위
 * 멀티스레드는 "동시성과 또는 병렬성"으로 실행되기 때문에 이 용어들에 대에 정확히 이해하는 것이 좋습니다.
 * - [동시성] 멀티 작업을 위해 하나의 코어에서 멀티스레드가 번갈아가며 실행하는 설질을 말합니다.
 * - [병렬성] 멀티 작업을 위해 멀티 코어에서 개별 스레드를 동시에 실행하는 설질을 말합니다.
 * <p>
 * #스레드스케줄링
 * 코어의 갯수보다 스레드의 갯수가 많다면, 스레드를 어떤 순서에 의해 동시성으로 실행할 것인가를 결정해야하는데
 * 이것을 스레드 스케케줄링이라고 합니다.
 * 스레드 스케줄링에 의해 스레드들은 아주 짧은 시간에 번갈아가며 그들의 run()메소드를 조금씩 실행합니다.
 * <p>
 * 스레드스케줄링은 2가지 방법을 갖습니다.
 * - [우선순위방식] 우선순위가 더 높은 스레드가 실행 상태를 더 많이 가지도록 스케줄링하는 것을 말합니다. (1 낮음, 10 높음, default 5)
 * - [순환할당방식] 시간 할당량을 정해서 하나의 스레드를 정해진 시간 만큼만 실행하고 다음으로 넘어가는 것을 말합니다.
 * <p>
 * #스레드그룹
 * 스레드를 묶어서 관리할 목적으로 이용
 * <p>
 * #스레드그룹생성과정
 * => JVM이 system 스레드 그룹을 만들고
 * => JVM이 운영에 필요한 스레드를 생성해 system 스레드그룹에 생성
 * => system의 하위 스레드 그룹으로 main을 만들고, 메인스레드를 이 구릅에 포함시킵니다.
 * <p>
 * #스레드그룹규칙
 * 스레드는 반드시 하나의 그룹에 포함
 * 명시적으로 주지 않으면 자신을 생성한 스레드와 같은 스레드 그룹에 속함
 * 대부분 메인스레드가 생성하기 때문에 메인그룹으로 들어감
 * <p>
 * #스레드풀
 * 작업처리에 사용되는 스레드를 제한된 개수만큼 [미리] 정해놓고
 * [작업큐]에 들어오는 작업들을 하나씩 스레드가 맡아 처리하는 방식을 말합니다.
 */

public class Doc_Thread {
    public static void main(String[] args) {

        // =========================================================================

        /**
         * #다양한스레드생성방법
         *
         * #API
         *  - start() :: 시작하기
         *  - getName() :: 이름얻기
         *  - setName(String name) :: 이름수정
         *  - currentThread() :: 현재스레드 참조
         *  - setPrioirity(int p) :: 우선순위 설정
         *  - getState() :: 스레드상태
         *
         * */

        // [CASE1] Runnable 구현객체로 부터 생성
        class Task implements Runnable {
            @Override
            public void run() {
                System.out.println("[스레드A 실행]");
            }
        }
        Runnable task = new Task();
        Thread threadA = new Thread(task);

        // [CASE2] Runnable 익명구현객체로 생성
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                System.out.println("[스레드B 실행]");
            }
        });

        // [CASE3] 람다식
        Thread threadC = new Thread(() -> {
            System.out.println("[스레드C 실행]");
        });

        // [CASE4] Thread 상속클래스로부터 생성
        class WorkerThread extends Thread {
            @Override
            public void run() {
                System.out.println("[스레드D 실행]");
            }
        }
        Thread threadD = new WorkerThread();

        // [CASE5] Thread 익명구현객체로 생성
        Thread threadE = new Thread() {
            public void run() {
                System.out.println("[스레드E 실행]");
            }
        };

        // Thread 실행 (run() 메소드 실행)
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        threadE.start();

        // =========================================================================

        /**
         * #동기화메소드&동기화블록
         *
         * */

        class Calculator {
            private int memory;

            public int getMemory() {
                return memory;
            }

            // [동기화메소드]
            public synchronized void setMemory(int memory) {
                this.memory = memory;
                try {
                    Thread.sleep(2000); //2초지연
                } catch (Exception e) {
                }
                System.out.println("[현재스레드] " + Thread.currentThread().getName() + " : " + this.memory);
            }

            public void clearMemory() {
                this.memory = 0;
                // [동기화블록]
                synchronized (this/*[공유객체]*/) {
                    // [임계역영]
                    try {
                        Thread.sleep(2000); //2초지연
                    } catch (Exception e) {
                    }
                    System.out.println("[현재스레드 ] " + Thread.currentThread().getName() + " : " + this.memory);
                }
            }
        }

        System.out.println();
        // =========================================================================

        /**
         * #스레드상태
         *  - 생성상태 (NEW)
         *  - 실행대기상태 (RUNNABLE)
         *  - 실행상태 (RUN)
         *  - 종료상태 (TERMINATE)
         *  - 정지상태 (WAITING, TIMED_WAITING, BLOCKED)
         *
         * #API
         *  - getState();
         * */

        Thread targetThread = new TargetThread();
        StatePrintThread spt = new StatePrintThread(targetThread);
        // spt.start();

        System.out.println();
        // =========================================================================

        /**
         * #스레드상태제어
         *
         * #API
         *  - interrupt() :: 일시정지상태 스레드에 예외를 살생시켜, 예외처리코드에서 [실행대기상태나 종료상태]로 이동
         *  - notify() :: 동기화 블록내에서 wait() 메소드에 의해 일시정지한 스레드를 [실행대기상태]로 만듬
         *  - notifyAll()
         *  - sleep(long millis) :: 주어진 시간동안 스레드를 [일시정지상태]로 만듬
         *  - sleep(long millis, int nanos)
         *  - join() :: 스레드가 [일시정지상태]가 됩니다.
         *  - join(long millis)
         *  - join(long millis, int nanos)
         *  - wait() :: 동기화블록내에서 스레드를 [일시정지상태], 시간이 지나거나 notify(), notifyAll() 에의해서 실행대기상태로 이동할 수 있음
         *  - wait(long millis)
         *  - wait(long millis, int nanos)
         *  - yield() :: 스레드 우선순위를 동일한 다른 스레드에게 양보후, 실행대기
         *  - resume() :: 스레드재실행
         *  // - stop() :: 스레드즉시종료
         *  // - suspend() :: 스레드를 일시정지 -> resume()
         *
         * */

        // sleep()
        //        Toolkit toolkit = Toolkit.getDefaultToolkit();
        //        for (int i = 0; i < 10; i++) {
        //            toolkit.beep();
        //            try {
        //                Thread.sleep(300);
        //            } catch (InterruptedException e) {}
        //        } Toolkit toolkit = Toolkit.getDefaultToolkit();
        //        //        for (int i = 0; i < 10; i++) {
        //        //            toolkit.beep();
        //        //            try {
        //        //                Thread.sleep(300);
        //        //            } catch (InterruptedException e) {}
        //        //        }

        // yield()
        //        SwitchA switchA = new SwitchA();
        //        SwitchB switchB = new SwitchB();
        //        switchA.start();
        //        switchB.start();
        //
        //        try {
        //            Thread.sleep(100);
        //        } catch (InterruptedException e) {}
        //        switchA.work = false;
        //
        //        try {
        //            Thread.sleep(100);
        //        } catch (InterruptedException e) {}
        //        switchA.work = true;
        //
        //        try {
        //            Thread.sleep(100);
        //        } catch (InterruptedException e) {}
        //        switchA.stop = true;
        //        switchB.stop = true;

        // join()
        SumThread sumThread = new SumThread();
        sumThread.start();
        try {
            sumThread.join(); // main 스레드를 중 (나만봐)
        } catch (InterruptedException e) {
        }
        System.out.println("[SumThread의계산값] " + sumThread.getSum());

        // wait() & notify()

        WorkObject shareObject = new WorkObject();
        Thread workThreadA = new PlayWorkObjectA(shareObject);
        Thread workThreadB = new PlayWorkObjectB(shareObject);
        // workThreadA.start();
        // workThreadB.start();

        // stop플래그 & interrupt()

        Thread stopFlagThread = new Thread() {
            private boolean flagStop;

            public void run() {
                while (!flagStop) { /**/ }
            }
        };

        Thread interruptThread = new Thread() {
            public void run() {
                try {
                    while (true) {
                        System.out.println("[Interrupt-Thread 실행중]");
                        /*[CASE1]*/
                        Thread.sleep(1);
                        // /*[CASE2]*/if(Thread.interrupted()) { break; }
                    }
                } catch (Exception e) {
                    System.out.println("[Interrupt-Thread interrupt()발생]");
                }
            }
        };
        // interruptThread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        // interruptThread.interrupt();

        System.out.println();
        // =========================================================================

        /**
         * #데몬스레드
         *  주스레드 작업을 돕는 보조적인 역할을 수행하는 스레드
         *  주스레드 종료시 데몬스레드도 같이 자동종료
         *
         * */

        AutoSaveThread ast = new AutoSaveThread();
        ast.setDaemon(true); // 데몬스레드로설정 (메인스레드종료와 함께종료)
        // ast.start();

        System.out.println();
        // =========================================================================

        /**
         * #스레드그룹
         *
         * #API
         *  - activeCount() => int :: 현재그룹 및 하위그룹에서 활동중인 모든 스레드
         *  - activeGroupCount() => int :: 현재그룹에서 활동중인 모든 하위그룹의 수
         *  - checkAccess() => void :: 현재스레드가 스레드 그룹을 변경할 권한이 있는지 체그
         *  - destroy() => void :: 현재그룹 및 하위그룹 모두 삭제
         *  - isDestroyed() => boolean :: 현재그룹 삭제여부
         *  - getMaxPriority() => int :: 현재그룹에 포함된 스레드가 가질수 있는 최대우선순위
         *  - setMaxPriority() => void :: 현재그룹에 포함된 스레드가 가질수 있는 최대우선순위
         *  - getName() => String :: 현재그룹 이름리턴
         *  - getParent() => TreadGroup :: 현재그룹의 부모그룹리턴
         *  - parentOf(ThreadGroup g) => boolean :: 현재그룹이 매개값을 지정한 스레드그룹의 부모인지
         *  - isDaemon() => boolean :: 데몬여부
         *  - setDaemon() => void :: 데몬그룹설정
         *  - list() => void :: 현재그룹에 포함된 스레드와 하위그룹에 대한 정보를 출력
         *  - interrupt() => void : 현재그룹의 모든 스레드 interrupt
         *
         * */

        AutoSaveThread astA = new AutoSaveThread();
        astA.setName("AutoSaveThread");
        astA.setDaemon(true);
        // astA.start();

        // 그룹조회
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        Set<Thread> threads = map.keySet();
        for (Thread t : threads) {
            System.out.println("[스레드이름] " + t.getName() + (t.isDaemon() ? "(데몬)" : "(메인)"));
            System.out.println("[스레드그룹] " + t.getThreadGroup().getName());
            System.out.println();
        }

        // 그룹생성
        ThreadGroup myGroup = new ThreadGroup("myGroup");
        GroupThread myGroupThread = new GroupThread(myGroup, "myGroupThread");
        myGroupThread.start();

        System.out.println();
        // =========================================================================

        /**
         * #스레드풀
         *
         * #API
         *  [생성]
         *      - newCachedThreadPool()
         *      - newFixedThreadPool(int nThreads)
         *  [종료]
         *      - shutdown() => void :: 현재처리중인 작업뿐 아니라 작업큐에 대기하고 있는 모든 작업을 처리뒤에 종료
         *      - shutdownNot() => List<Runable> :: 현재작업처리중인 스레드를 interrupt해서 작업중지를 시도하고 스레드풀을 종료, 미처리된 작업목록이 리턴
         *      - awaitTermination(long timeout, TimeUnit unit) => boolean :: 종료호출이후, 모든작업처리 timeout내에 완료하면 (true) / 완료못하면 interrupt후에 (false)
         *  [작업]
         *      - execute(Runnable command) => void :: Runnable을 작업큐에 저장 (작업처리결과 받지못함)
         *      - submit(Runnable task) => Future<?> :: Runnable 또는 Callable을 작업큐에 저장 (Future를 통해 작업처리결과 얻음)
         *      - submit(Runnable task, V result) => Future<V> :: Runnable 또는 Callable을 작업큐에 저장 (Future를 통해 작업처리결과 얻음)
         *      - submit(Callable<V> task) => Future<V> :: Runnable 또는 Callable을 작업큐에 저장 (Future를 통해 작업처리결과 얻음)
         *
         */

        //        ExecutorService executorServiceA = Executors.newCachedThreadPool();
        //        ExecutorService executorServiceB = Executors.newFixedThreadPool(5);
        //        ExecutorService executorServiceC = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // CPU 코어수 (4)
        //        ExecutorService executorServiceD = new ThreadPoolExecutor(
        //                3,                                // 코어 스레드 갯수
        //                100,                              // 최대 스레드 갯수
        //                120L,                             // 놀고 있는 시간
        //                TimeUnit.SECONDS,                 // 놀고 있는 시간 단위
        //                new SynchronousQueue<Runnable>()  // 작업큐
        //        );

        // 작업요청
        ExecutorService es = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
                    int poolSize = tpe.getPoolSize();
                    String threadName = Thread.currentThread().getName();
                    System.out.printf("[총스레드갯수 - %d] 작업스레드이름 : %s \n", poolSize, threadName);

                    // 예외발생
                    int value = Integer.parseInt("삼");
                }
            };

            // es.execute(r);
            // es.submit(r);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
        es.shutdown();

        // =========================================================================

        /**
         * #블로킹방식의 작업완료통보
         *  - [CASE1] 리턴값이없는작업통보
         *  - [CASE2] 리턴값이있는작업통보
         *  - [CASE3] 작업처리결과를 외부객체에저장
         *  - [CASE4] 작업완료순으로통보
         *  - [CASE5] 콜백방식의작업완료통보
         *
         * #API
         *  - submit(Runnable task) => Future<?> :: Runnable 또는 Callable을 작업큐에 저장 (Future를 통해 작업처리결과 얻음)
         *  - submit(Runnable task, V result) => Future<V> :: Runnable 또는 Callable을 작업큐에 저장 (Future를 통해 작업처리결과 얻음)
         *  - submit(Callable<V> task) => Future<V> :: Runnable 또는 Callable을 작업큐에 저장 (Future를 통해 작업처리결과 얻음)
         *
         * #Future객체
         *  - get() => V :: 작업이 완료될때까지 블로킹되었다가 처리결과 V리턴
         *  - get(long timeout, TimeUnit unit) => V :: timeout시간전에 작업이 완료되면 결과 V리턴, 완료되지 않을시 예외처리
         *  - cancel(boolean mayInterruptIfRunning) => boolean :: 작업처리가 진행중일 경우 취소
         *  - isCancelled() => boolean :: 작업취소?
         *  - isDone() => boolean :: 작업완료?
         *
         * #CompletionService
         *  - poll() => Future<V> :: 완료된작업의 Futrue를 가져옴  (없다면 null리턴)
         *  - poll(long timeout, TimeUnit unit) => Future<V> :: 완료된 작업의 Future를 가져옴 (없다면 timeout까지 블로킹)
         *  - take() => Future<V> :: 완료된 작업의 Future를 가져옴 (없다면 있을때까지 블로킹)
         *  - submit(Callable<V> task) => Future<V> :: 스레드풀에 Callable 작업처리요청
         *  - submit(Runnable task, V result) => Future<V> :: 스레드풀에 Runnable 작업처리요청
         * */

        ExecutorService esB = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 10); // CPU 코어수 (4)

        // [CASE1] 리턴값이없는작업통보
        System.out.println("[작업처리요청]");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i <= 10; i++) sum += i;
                System.out.println("[처리결과] " + sum);
            }
        };
        Future future = esB.submit(r);
        try {
            future.get();
            System.out.println("[작업처리완료]");
        } catch (InterruptedException e) {
            System.out.println("[실행예외발생] " + e.getMessage());
        } catch (ExecutionException e) {
        }
        System.out.println();

        // [CASE2] 리턴값이있는작업통보
        System.out.println("[작업처리요청]");
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum = 0;
                for (int i = 0; i <= 10; i++) sum += i;
                return sum;
            }
        };
        Future<Integer> futureB = esB.submit(callable);
        try {
            int sum = futureB.get();
            System.out.println("[처리결과] " + sum);
            System.out.println("[작업처리완료]");
        } catch (Exception e) {
            System.out.println("[실행예외발생] " + e.getMessage());
        }
        System.out.println();

        // [CASE3] 작업처리결과를 외부객체에저장
        System.out.println("[작업처리요청]");
        class SaveOther implements Runnable {
            Result result;

            SaveOther(Result result) {
                this.result = result;
            }

            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i <= 10; i++) sum += i;
                result.addValue(sum);
            }
        }
        Result result = new Result();
        Runnable taskA = new SaveOther(result);
        Runnable taskB = new SaveOther(result);
        Future<Result> futureC = esB.submit(taskA, result);
        Future<Result> futureD = esB.submit(taskB, result);
        try {
            result = futureC.get();
            result = futureD.get();
            System.out.println("[처리결과] " + result.accumValue);
            System.out.println("[작업처리완료]");
        } catch (Exception e) {
            System.out.println("[실행예외발생] " + e.getMessage());
        }
        System.out.println();

        // [CASE4] 작업완료순으로통보

        // [E] java.util.concurrent.RejectedExecutionException
        // - Executor 가 그의 책임 아래 더이상 task 들을 처리 할 수 없을때
        // - shutdown 메소드가 호출 된 후 Executor 가 실행 되는것을 주의 해서 프로그래밍을 한다.
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(esB);
        System.out.println("[작업처리요청]");
        for (int i = 0; i < 3; i++) {
            cs.submit(callable);
        }
        System.out.println("[처리완료된작업확인]");
        esB.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Future<Integer> future = cs.take();
                        int value = future.get();
                        System.out.println("[처리결과] " + value);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        System.out.println();

        // [CASE5] 콜백방식의작업완료통보
        CallbackExample cb = new CallbackExample(esB);
        cb.doWork("3", "3");
        cb.doWork("3", "4");
        cb.doWork("3", "?");
        cb.doWork("3", "삼");

        esB.shutdown(); // cb.finish();
        // =========================================================================


    }

    static class StatePrintThread extends Thread {
        private Thread target;

        public StatePrintThread(Thread target) {
            this.target = target;
        }

        public void run() {
            while (true) {
                Thread.State state = target.getState();
                System.out.println("[타겟스레드 상태] " + state);

                if (state == Thread.State.NEW) {
                    target.start();
                }

                if (state == Thread.State.TERMINATED) {
                    break;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    static class TargetThread extends Thread {
        public TargetThread() {

        }

        public void run() {
            // RUNNABLE
            for (long i = 0; i < 1000000000; i++) {
            }

            // TIMED_WAITING
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }

            // RUNNABLE
            for (long i = 0; i < 1000000000; i++) {
            }
        }
    }

    static class SwitchA extends Thread {
        public boolean stop = false;
        public boolean work = true;

        public void run() {
            while (!stop) {
                if (work) {
                    System.out.println("[SWITCH_A 실행]");
                } else {
                    Thread.yield();
                }
            }
            System.out.println("[SWITCH_A 종료]");
        }
    }

    static class SwitchB extends Thread {
        public boolean stop = false;
        public boolean work = true;

        public void run() {
            while (!stop) {
                if (work) {
                    System.out.println("[SWITCH_B 실행]");
                } else {
                    Thread.yield();
                }
            }
            System.out.println("[SWITCH_B 종료]");
        }
    }

    static class SumThread extends Thread {
        private long sum;

        public long getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public void run() {
            for (int i = 0; i <= 100; i++) {
                sum += i;
            }
        }
    }

    static class WorkObject {
        public synchronized void methodA() {
            System.out.println("[ThreadA의 methodA()작업] " + Thread.currentThread().getName());
            notify();
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        public synchronized void methodB() {
            System.out.println("[ThreadA의 methodB()작업] " + Thread.currentThread().getName());
            notify();
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    static class PlayWorkObjectA extends Thread {
        private WorkObject wo;

        public PlayWorkObjectA(WorkObject wo) {
            this.wo = wo;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                wo.methodA();
            }
        }
    }

    static class PlayWorkObjectB extends Thread {
        private WorkObject wo;

        public PlayWorkObjectB(WorkObject wo) {
            this.wo = wo;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                wo.methodB();
            }
        }
    }

    static class AutoSaveThread extends Thread {
        public void save() {
            System.out.println("[내용저장]");
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                save();
            }
        }
    }

    static class GroupThread extends Thread {
        public GroupThread(ThreadGroup tg, String name) {
            super(tg, name);
        }

        public void run() {

        }
    }

    static class Result {
        int accumValue;

        synchronized void addValue(int value) {
            accumValue += value;
        }
    }

    static class CallbackExample {
        private ExecutorService es;

        CallbackExample(ExecutorService es) {
            this.es = es;
        }

        private CompletionHandler<Integer, Void> callback = new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(Integer result, Void attachment) {
                System.out.println("[completed()실행] " + result);
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("[failed()실행] " + exc.toString());
            }
        };

        public void doWork(final String x, final String y) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        int intX = Integer.parseInt(x);
                        int intY = Integer.parseInt(y);
                        int result = intX + intY;
                        callback.completed(result, null);
                    } catch (NumberFormatException e) {
                        callback.failed(e, null);
                    }
                }
            };
            es.submit(r);
        }

        public void finish() {
            es.shutdown();
        }
    }


}