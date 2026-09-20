package helium314.keyboard.toptopy

import androidx.activity.ComponentActivity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.edit
import helium314.keyboard.keyboard.KeyboardSwitcher
import helium314.keyboard.latin.R
import helium314.keyboard.latin.settings.Settings as KeyboardSettings
import helium314.keyboard.latin.common.FileUtils

class TopTopyStudioActivity : ComponentActivity() {
    private lateinit var content: LinearLayout
    private val bgPicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            val file = KeyboardSettings.getCustomBackgroundFile(this, false, false)
            FileUtils.copyContentUriToNewFile(uri, this, file)
            KeyboardSettings.clearCachedBackgroundImages()
            KeyboardSwitcher.getInstance().setThemeNeedsReload()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(7,8,14)
        buildUi()
    }

    private fun buildUi() {
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL; setPadding(18,18,18,18); background=gradient(0xFF07080E.toInt(),0xFF17112A.toInt())}
        val title=TextView(this).apply{text="TOP TOPY  •  STUDIO"; textSize=22f; setTextColor(Color.WHITE); setPadding(4,8,4,4)}
        val sub=TextView(this).apply{text="Fonts  •  Symbols  •  Kaomoji  •  Text Art  •  Themes"; textSize=12f; setTextColor(0xFFB8B7C8.toInt()); setPadding(4,0,4,12)}
        root.addView(title); root.addView(sub)
        val tabs=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL}
        listOf("Fonts","Kaomoji","Symbols","Text Art","Themes").forEachIndexed{ i,t -> val b=button(t); b.setOnClickListener{show(i)}; tabs.addView(b, LinearLayout.LayoutParams(0,52,1f).apply{setMargins(3,3,3,3)})}
        root.addView(tabs)
        val scroll=ScrollView(this); content=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL; setPadding(2,12,2,20)}; scroll.addView(content); root.addView(scroll,LinearLayout.LayoutParams(-1,0,1f))
        setContentView(root); show(0)
    }

    private fun show(tab:Int){content.removeAllViews(); when(tab){
        0 -> {
            TopTopyTextStyles.Style.entries.forEach { style -> addFontAction(style) }
            addHint("Tap a style to convert the selected text or the current word. These are Unicode styles, so they work across many apps.")
        }
        1 -> listOf("(｡♥‿♥｡)","(づ｡◕‿‿◕｡)づ","(╥﹏╥)","(ง'̀-'́)ง","(•_•)","(¬‿¬)","(ʘ‿ʘ)","♡(˃͈ દ ˂͈ ༶ )","¯\_(ツ)_/¯","(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧").forEach{addAction(it){sendInsert(it)}}
        2 -> listOf("♡","♥","☆","★","✦","✧","⚡","☾","☽","♛","♕","∞","꧁","꧂","『","』","【","】","※","彡","ツ","亗","×","✓","→","←","↑","↓").forEach{addAction(it){sendInsert(it)}}
        3 -> listOf("╔════════════╗","║  TOP TOP Y  ║","╚════════════╝","━━━━━━━✦━━━━━━━","┏━━━━━━━━━━━━┓","┗━━━━━━━━━━━━┛","꧁༺ TOP TOP Y ༻꧂","『 T O P  T O P Y 』","★彡 TOP TOP Y 彡★").forEach{addAction(it){sendInsert(it+" ")}}
        4 -> {
            addAction("🌙  Dark / Light / System  →  Appearance") { startActivity(Intent(this, helium314.keyboard.settings.SettingsActivity2::class.java)) }
            addAction("🖼  Change Keyboard Background") { bgPicker.launch("image/*") }
            addAction("♻  Restore Built-in Background") { KeyboardSettings.getCustomBackgroundFile(this,false,false).delete(); KeyboardSettings.clearCachedBackgroundImages(); KeyboardSwitcher.getInstance().setThemeNeedsReload() }
            addHint("The built-in background is the bundled Top Topy image. Full keyboard appearance controls remain available in Appearance settings.")
        }
    }}

    private fun addFontAction(style: TopTopyTextStyles.Style) {
        val label = "${style.title}   ${style.sample}"
        addAction(label) { sendTransform(style) }
    }

    private fun addAction(label:String, action:()->Unit){ val b=button(label); b.setOnClickListener{action()}; content.addView(b,LinearLayout.LayoutParams(-1,56).apply{setMargins(0,5,0,5)}) }
    private fun addHint(text:String){val t=TextView(this).apply{text=text; textSize=12f; setTextColor(0xFFAAA9BA.toInt()); setPadding(10,14,10,14)}; content.addView(t)}
    private fun button(text:String)=Button(this).apply{this.text=text; textSize=14f; isAllCaps=false; setTextColor(Color.WHITE); background=gradient(0xFF24243A.toInt(),0xFF171727.toInt()); gravity=Gravity.CENTER; minHeight=0}
    private fun gradient(a:Int,b:Int)=GradientDrawable(GradientDrawable.Orientation.TL_BR,intArrayOf(a,b)).apply{cornerRadius=18f}

    private fun sendInsert(text:String){sendBroadcast(Intent(ACTION).setPackage(packageName).putExtra(EXTRA_TEXT,text))}
    private fun sendTransform(style:TopTopyTextStyles.Style){sendBroadcast(Intent(ACTION).setPackage(packageName).putExtra(EXTRA_STYLE,style.name))}

    companion object { const val ACTION="helium314.keyboard.TOP_TOPY_STUDIO"; const val EXTRA_TEXT="text"; const val EXTRA_STYLE="style" }
}
