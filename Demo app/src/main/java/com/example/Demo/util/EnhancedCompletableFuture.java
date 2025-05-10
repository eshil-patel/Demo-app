/*
package com.example.Demo.util;

import java.lang.invoke.VarHandle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinTask;

public class EnhancedCompletableFuture<T>  extends CompletableFuture<T> {

    static final int ASYNC  =  1;

    volatile Object result;       // Either the result or boxed AltResult
    volatile BiRelay stack;    // Top of Treiber stack of dependent actions

    private static final VarHandle RESULT;

    public CompletableFuture<Void> allOfWithSpecifiedExecutor(CompletableFuture<?>... cfs){

    }

    public static CompletableFuture<Void> allOf(Executor executor,CompletableFuture<?>... cfs) {
        return andTree(cfs, 0, cfs.length - 1, executor);
    }

    public EnhancedCompletableFuture<T> transformToEnhancedCf(CompletableFuture<T> cf){
        return new EnhancedCompletableFuture<>();
    }

    static EnhancedCompletableFuture<Void> andTree(CompletableFuture<?>[] cfs,
                                           int lo, int hi, Executor executor) {
        EnhancedCompletableFuture<Void> d = new EnhancedCompletableFuture<Void>();
        if (lo > hi) // empty
            d.result = NIL;
        else {
            EnhancedCompletableFuture<?> a, b; Object r, s, z; Throwable x;
            int mid = (lo + hi) >>> 1;
            if ((a = (lo == mid ? cfs[lo] :
                    andTree(cfs, lo, mid,executor))) == null ||
                    (b = (lo == hi ? a : (hi == mid+1) ? cfs[hi] :
                            andTree(cfs, mid+1, hi,executor))) == null)
                throw new NullPointerException();
            if ((r = a.result) == null || (s = b.result) == null)
                a.bipush(b, new BiRelay<>(d, a, b));
            else if ((r instanceof AltResult
                    && (x = ((AltResult)(z = r)).ex) != null) ||
                    (s instanceof AltResult
                            && (x = ((AltResult)(z = s)).ex) != null))
                d.result = encodeThrowable(x, z);
            else
                d.result = NIL;
        }
        return d;
    }

    static final class BiRelay<T,U> extends BiCompletion<T,U,Void> { // for And
        BiRelay(EnhancedCompletableFuture<Void> dep,
                EnhancedCompletableFuture<T> src, EnhancedCompletableFuture<U> snd) {
            super(null, dep, src, snd);
        }
        final EnhancedCompletableFuture<Void> tryFire(int mode) {
            EnhancedCompletableFuture<Void> d;
            EnhancedCompletableFuture<T> a;
            EnhancedCompletableFuture<U> b;
            Object r, s, z; Throwable x;
            if (   (a = src) == null || (r = a.result) == null
                    || (b = snd) == null || (s = b.result) == null
                    || (d = dep) == null)
                return null;
            if (d.result == null) {
                if ((r instanceof AltResult
                        && (x = ((AltResult)(z = r)).ex) != null) ||
                        (s instanceof AltResult
                                && (x = ((AltResult)(z = s)).ex) != null))
                    d.completeThrowable(x, z);
                else
                    d.completeNull();
            }
            src = null; snd = null; dep = null;
            return d.postFire(a, b, mode);
        }
    }

    abstract static class BiCompletion<T,U,V> extends UniCompletion<T,V> {
        EnhancedCompletableFuture<U> snd; // second source for action
        BiCompletion(Executor executor, EnhancedCompletableFuture<V> dep,
                     EnhancedCompletableFuture<T> src, EnhancedCompletableFuture<U> snd) {
            super(executor, dep, src); this.snd = snd;
        }
    }

    abstract static class UniCompletion<T,V> extends Completion {
        Executor executor;                 // executor to use (null if none)
        EnhancedCompletableFuture<V> dep;          // the dependent to complete
        EnhancedCompletableFuture<T> src;          // source for action

        UniCompletion(Executor executor, EnhancedCompletableFuture<V> dep,
                      EnhancedCompletableFuture<T> src) {
            this.executor = executor; this.dep = dep; this.src = src;
        }

        */
/**
         * Returns true if action can be run. Call only when known to
         * be triggerable. Uses FJ tag bit to ensure that only one
         * thread claims ownership.  If async, starts as task -- a
         * later call to tryFire will run action.
         *//*

        final boolean claim() {
            Executor e = executor;
            if (compareAndSetForkJoinTaskTag((short)0, (short)1)) {
                if (e == null)
                    return true;
                executor = null; // disable
                e.execute(this);
            }
            return false;
        }

        final boolean isLive() { return dep != null; }
    }

    abstract static class Completion extends ForkJoinTask<Void>
            implements Runnable, AsynchronousCompletionTask {
        volatile Completion next;      // Treiber stack link

        */
/**
         * Performs completion action if triggered, returning a
         * dependent that may need propagation, if one exists.
         *
         * @param mode SYNC, ASYNC, or NESTED
         *//*

        abstract CompletableFuture<?> tryFire(int mode);

        */
/** Returns true if possibly still triggerable. Used by cleanStack. *//*

        abstract boolean isLive();

        public final void run()                { tryFire(ASYNC); }
        public final boolean exec()            { tryFire(ASYNC); return false; }
        public final Void getRawResult()       { return null; }
        public final void setRawResult(Void v) {}
    }

    static final class AltResult { // See above
        final Throwable ex;        // null only for NIL
        AltResult(Throwable x) { this.ex = x; }
    }

    final boolean completeThrowable(Throwable x, Object r) {
        return RESULT.compareAndSet(this, null, encodeThrowable(x, r));
    }

    final boolean completeNull() {
        return RESULT.compareAndSet(this, null, NIL);
    }
}
*/
