
import android.util.Log

const val MAX_LOG_TAG_LENGTH = 23
const val LOG_PREFIX         = "MT_"
const val LOG_PREFIX_LENGTH  = LOG_PREFIX.length

inline fun <reified T> T.info(msg: Any) = Log.i(getTag(T::class.java.simpleName), msg.toString())
inline fun <reified T> T.verbose(msg: Any) = Log.v(getTag(T::class.java.simpleName), msg.toString())
inline fun <reified T> T.debug(msg: Any) = Log.d(getTag(T::class.java.simpleName), msg.toString())
inline fun <reified T> T.warn(msg: Any, e: Throwable? = null) = Log.w(getTag(T::class.java.simpleName), msg.toString(), e)
inline fun <reified T> T.error(msg: Any, e: Throwable? = null) =
        Log.e(getTag(T::class.java.simpleName), msg.toString()+' '+e?.let { Log.getStackTraceString(it) }, e)

fun getTag(tag: String) = if (tag.length > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) tag.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1)+tag else LOG_PREFIX+tag