package jp.techacademy.kenji.takada.taskapp

import android.content.Context
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

//引数が入ってくる
class TaskAdapter(context: Context) : BaseAdapter(){

    private val mLayoutInflater: LayoutInflater

    //   この個所を　Task型に　変更
    var taskList = mutableListOf<Task>()

    init {
        this.mLayoutInflater = LayoutInflater.from(context)
    }

//    Method

//  Listの数
    override fun getCount(): Int {
        return taskList.size
    }

//  Listの位置
    override fun getItem(position: Int): Any {
        return taskList[position]
    }

//  List　の　IDは　戻さない　→　変更　
    override fun getItemId(position: Int): Long {
        return taskList[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: mLayoutInflater.inflate(android.R.layout.simple_list_item_2, null)

        val textView1 = view.findViewById<TextView>(android.R.id.text1)
        val textView2 = view.findViewById<TextView>(android.R.id.text2)

        textView1.text = taskList[position].title


        // 後でTaskクラスから情報を取得するように変更する 仮
        // textView1.text = taskList[position]
        //    ↓　変更
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPANESE)
        val date = taskList[position].date
        textView2.text = simpleDateFormat.format(date)


        return view
    }






}
//Class END