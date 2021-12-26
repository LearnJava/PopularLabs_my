package ru.konstantin.popularlabs_my.switchmap

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import kotlin.random.Random

class Producer {
    fun createJust() = Observable.just("1", "2", "3", "3")

    /**
     * Ответ на 2 пунт ДЗ:
     * SwithMap() кардинально отличается от FlatMap() тем, что игнорирует промежуточные результаты
     * выводит только последний. SwitchMap отписывается от предыдущего источника Observable
     * каждый раз, когда новый элемент начинает излучать данные, тем самым всегда
     * эмитит данные из текущего Observable.
     */

    fun execFlatMap() {
        createJust()
            .switchMap {
                val delay = Random.nextInt(1000).toLong()
                return@switchMap Observable.just(it + "x").delay(delay,
                    java.util.concurrent.TimeUnit.MILLISECONDS
                )
            }
            .subscribe({ s ->
                Log.d("mylogs", "onNext: $s")
            }, {
                Log.d("mylogs","onError: ${it.message}")
            })
    }
}