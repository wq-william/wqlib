package hz.wq.common.util.other

/**
 * 创建者: W~Q
 * 创建时间:   2024/7/22 09:55
 * 描述: TODO
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER)
annotation class SingleClick constructor(
    /**
     * 快速点击的间隔
     */
    val value: Long = 1000
)