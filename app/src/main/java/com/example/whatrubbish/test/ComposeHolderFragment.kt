package com.example.whatrubbish.test


//import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.compose.ui.theme.ComposeTheme
import kotlin.reflect.KProperty

//import java.lang.reflect.Modifier

//public class ComposeHolderFragment {
//}
class ComposeHolderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column() {
                    MyTest()
                    RadioButtonDemo()
                }

//                RadioButtonDemo()
            }
        }
    }

    @Composable
    fun MyTest() {
        Text(text = "hello compose")
    }



}

@Composable
fun RadioButtonDemo() {
    val tags = arrayListOf("Java", "Kotlin", "XML", "Compose", "JetPack")
    var selectedTag = remember { mutableStateOf("Null") }
//    RadioButton 和 text 绑定 compose
//    var selectedTag by rememberSaveable { mutableStateOf("") }
//
    Row() {
        tags.forEach {
            Row {
                RadioButton(
                    selected = it == selectedTag.value,
//                    循环过来 现在 列表里的这个数据 是 选中的吗
                    onClick = {
//                        现在选中的值 是他了
                        selectedTag.value = it
                    }
                )

//                Text(text = it)

//                Text(text = it,modifier = Modifier.clickable {
//                    selectedTag = it
//                })
            }

            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

//private operator fun Any.getValue(nothing: Nothing?, property: KProperty<*>): Any {
// return null;
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RadioButtonDemo()
//    ComposeTheme {
//        RadioButtonDemo()
//    }
}
