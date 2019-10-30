package photograd.kz.smart.ui.fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kz.smart.calendar.Constants
import kz.smart.calendar.R

const val BEGIN = 0
const val AND = " и "
const val SPACE = " "

class UserAgreementFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userAgreementText = inflater.inflate(R.layout.fragment_user_agreement, container, false) as TextView

        val authWarningStr = resources.getString(R.string.authorization_warning)
        val userAgreementStr = resources.getString(R.string.user_agreement)
        val privacyPolicyStr = resources.getString(R.string.privacy_policy)

        val authWarningStrBuilder = SpannableStringBuilder(authWarningStr)

        val agreementClickable = createClickableSpan(Constants.termsOfUseLink)
        val privacyClickable = createClickableSpan(Constants.privacyPolicyLink)

        val userAgreementStrBuilder = createSpannableStrBuilder(
            clickableSpan = agreementClickable,
            spanString = userAgreementStr
        )
        val privacyPolicyStrBuilder = createSpannableStrBuilder(
            clickableSpan = privacyClickable,
            spanString = privacyPolicyStr
        )
        val fullWarningStr = authWarningStrBuilder
            .append(SPACE)
            .append(userAgreementStrBuilder)
            .append(AND)
            .append(privacyPolicyStrBuilder)

        userAgreementText.text = fullWarningStr
        userAgreementText.movementMethod = LinkMovementMethod.getInstance()

        return userAgreementText
    }

    private fun createClickableSpan(url: String) = object : ClickableSpan() {

        override fun onClick(p0: View) {
            val openBrowserIntent = Intent(Intent.ACTION_VIEW)
            openBrowserIntent.data = Uri.parse(url)
            startActivity(openBrowserIntent)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.isFakeBoldText = true
        }
    }

    private fun createSpannableStrBuilder(
        clickableSpan: ClickableSpan,
        spanString: String
    ) = SpannableStringBuilder(spanString).apply {
        setSpan(
            clickableSpan,
            BEGIN,
            spanString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }


}
