package com.tsyrkunou.jmpwep.application.utils.softexecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;

public final class Try {
    private Try() {
    }

    public static <T> Executor<T> execute(Supplier<T> supplier) {
        return new Executor<>(supplier);
    }

    ;

    @RequiredArgsConstructor
    public static class ConsumerHandler {
        private final Class<? extends Exception> exClazz;
        private final Consumer<Exception> consumer;
    }

    @RequiredArgsConstructor
    public static class FunctionHandler<T> {
        private final Class<? extends Exception> exClazz;
        private final Function<Exception, T> function;
    }

    public static class Executor<T> {
        private final Supplier<T> supplier;

        private final Map<String, ConsumerHandler> consumerHandlerMap = new HashMap<>();
        private final Map<String, FunctionHandler<T>> functionHandlerMap = new HashMap<>();

        public Executor(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        private Consumer<Exception> anyCase;

        public final Executor<T> handling(ConsumerHandler... consumerHandlers) {
            for (ConsumerHandler consumerHandler : consumerHandlers) {
                consumerHandlerMap.put(consumerHandler.exClazz.getName(), consumerHandler);
            }
            return this;
        }

        public Executor<T> handling(FunctionHandler<T>... functionHandlers) {
            for (FunctionHandler<T> functionHandler : functionHandlers) {
                functionHandlerMap.put(functionHandler.exClazz.getName(), functionHandler);
            }
            return this;
        }

        public Executor<T> forEachCase(Consumer<Exception> exceptionConsumer) {
            this.anyCase = exceptionConsumer;
            return this;
        }

        public T execute() {
            try {
                return supplier.get();
            } catch (Exception e) {
                Try.ConsumerHandler exceptionConsumer = null;
                Try.FunctionHandler<T> exceptionHandler = null;
                Class<?> eClass = e.getClass();
                if (!consumerHandlerMap.isEmpty() || !functionHandlerMap.isEmpty()) {
                    do {
                        exceptionConsumer = consumerHandlerMap.get(eClass.getName());
                        exceptionHandler = functionHandlerMap.get(eClass.getName());
                        eClass = eClass.getSuperclass();
                    } while (exceptionConsumer == null
                            && exceptionHandler == null
                            && Throwable.class.isAssignableFrom(eClass));
                }
                if (anyCase != null) {
                    anyCase.accept(e);
                }
                if (exceptionConsumer != null) {
                    exceptionConsumer.consumer.accept(e);
                    return null;
                } else if (exceptionHandler != null) {
                    return exceptionHandler.function.apply(e);
                } else {
                    throw e;
                }
            }
        }

    }
}
