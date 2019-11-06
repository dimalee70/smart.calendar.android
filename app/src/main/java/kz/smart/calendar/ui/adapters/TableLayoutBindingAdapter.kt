package kz.smart.calendar.ui.adapters

import android.content.Context
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import kz.smart.calendar.R
import kz.smart.calendar.ui.common.PollTextView

import java.util.regex.Pattern

object TableLayoutBindingAdapter {

    private val sColumnPattern = Pattern.compile("\\s*,\\s*")

    private val MAX_COLUMNS = 20

    @BindingAdapter("android:collapseColumns")
    fun setCollapseColumns(view: TableLayout, columnsStr: CharSequence) {
        val columns = parseColumns(columnsStr)
        for (i in 0 until MAX_COLUMNS) {
            val isCollapsed = columns.get(i, false)
            if (isCollapsed != view.isColumnCollapsed(i)) {
                view.setColumnCollapsed(i, isCollapsed)
            }
        }
    }

    @BindingAdapter("android:shrinkColumns")
    fun setShrinkColumns(view: TableLayout, columnsStr: CharSequence?) {
        if (columnsStr != null && columnsStr.length > 0 && columnsStr[0] == '*') {
            view.isShrinkAllColumns = true
        } else {
            view.isShrinkAllColumns = false
            val columns = parseColumns(columnsStr)
            val columnCount = columns.size()
            for (i in 0 until columnCount) {
                val column = columns.keyAt(i)
                val shrinkable = columns.valueAt(i)
                if (shrinkable) {
                    view.setColumnShrinkable(column, shrinkable)
                }
            }
        }
    }

    @BindingAdapter("android:stretchColumns")
    fun setStretchColumns(view: TableLayout, columnsStr: CharSequence?) {
        if (columnsStr != null && columnsStr.length > 0 && columnsStr[0] == '*') {
            view.isStretchAllColumns = true
        } else {
            view.isStretchAllColumns = false
            val columns = parseColumns(columnsStr)
            val columnCount = columns.size()
            for (i in 0 until columnCount) {
                val column = columns.keyAt(i)
                val stretchable = columns.valueAt(i)
                if (stretchable) {
                    view.setColumnStretchable(column, stretchable)
                }
            }
        }
    }

    private fun parseColumns(sequence: CharSequence?): SparseBooleanArray {
        val columns = SparseBooleanArray()
        if (sequence == null) {
            return columns
        }
        val columnDefs = sColumnPattern.split(sequence)

        for (columnIdentifier in columnDefs) {
            try {
                val columnIndex = Integer.parseInt(columnIdentifier)
                // only valid, i.e. positive, columns indexes are handled
                if (columnIndex >= 0) {
                    // putting true in this sparse array indicates that the
                    // column index was defined in the XML file
                    columns.put(columnIndex, true)
                }
            } catch (e: NumberFormatException) {
                // we just ignore columns that don't exist
            }

        }

        return columns
    }

    @BindingAdapter("setImage")
    fun ImageView.setImage(url: String){
        if(!url.isNullOrEmpty())
        {
            Glide.with(context)
                .load(url)
                .apply(createOptionForGlide())
                .into(this)
        }
    }

    fun createOptionForGlide(): RequestOptions {
        return RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.progress_animation)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform()
    }


    @JvmStatic
    @BindingAdapter("textInt")
    fun TextView.setTextInt(num: Int){
        text =  num.toString()
    }

    @JvmStatic
    @BindingAdapter("statusText")
    fun TextView.setStatusText(num: Int){
        text = if (num == 1) resources.getString(R.string.poll_status_active) else resources.getString(R.string.poll_status_no_active)
    }

    @JvmStatic
    @BindingAdapter("percentageText")
    fun TextView.setPercantageText(num: Float?){
        if(num == null) {
            text = ""
            return
        }
        text = num.toString() + "%"
    }

    @JvmStatic
    @BindingAdapter("isSelected")
    fun PollTextView.setIsSelected(select: Boolean){
        val drawable = ContextCompat.getDrawable(context, R.drawable.bg_rounded_ans)!!
        if(select){
            val wrappedDrawable = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, R.color.frameVoteColorChosen))
            background = wrappedDrawable
        }
        else{
            val wrappedDrawable = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, R.color.frameVoteColorUnChosen))
            background = wrappedDrawable
        }
    }
}