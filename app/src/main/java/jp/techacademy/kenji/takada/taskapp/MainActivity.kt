package jp.techacademy.kenji.takada.taskapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.Sort

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity<E> : AppCompatActivity() {

    //    Realm 関連＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    //    Realm型変数
    private lateinit var mRealm: Realm

    //    mRealmListenerは　RealmのDBに追加や削除など変化があった場合に呼ばれるListener
    private val mRealmListener = object : RealmChangeListener<Realm>{
        override fun onChange(element: Realm) {
            reloadListView()
        }
    }

    // ListView　関連＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    private lateinit var mTaskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Realmの設定＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
        mRealm = Realm.getDefaultInstance()
        // mRealmListenerをaddChangeListenodで設定
        mRealm.addChangeListener(mRealmListener)



        // ListViewの設定＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
//        TaskAdapterを保持するPropertyを定義　TaskAdapterを生成
        mTaskAdapter = TaskAdapter(this@MainActivity)

        // ListViewをタップしたときの処理
        listView1.setOnItemClickListener { parent, view, position, id ->
            // 入力・編集する画面に遷移させる
        }

        // ListViewを長押ししたときの処理
        listView1.setOnItemLongClickListener { parent, view, position, id ->
            // タスクを削除する
            true
        }

        // アプリ起動時に表示テスト用のタスクを作成する　　Realm関連
        addTaskForTest()

        // Method 使用　 // ListView関連
        reloadListView()
    }




    // ListViewの設定　Method＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    private fun reloadListView() {

//仮置き
//        // 後でTaskクラスに変更する　(仮)
//        val taskList = mutableListOf("aaa", "bbb", "ccc")
//        mTaskAdapter.taskList = taskList
//        listView1.adapter = mTaskAdapter
//        mTaskAdapter.notifyDataSetChanged()

        // Realmデータベースから、「全てのDataを取得して新しい日時順に並べた結果」を取得
        val taskRealmResults = mRealm.where(Task::class.java).findAll().sort("date", Sort.DESCENDING)

        // 上記の結果を、TaskList としてSetする
        mTaskAdapter.taskList = mRealm.copyFromRealm(taskRealmResults)


        // TaskのListView用のアダプタに渡す
        listView1.adapter = mTaskAdapter

        // 表示を更新するために、アダプターにデータが変更されたことを知らせる
        mTaskAdapter.notifyDataSetChanged()
    }

    //    RealmClassのObjectはcloseMethodで終了させる必要があります。
    override fun onDestroy() {
        super.onDestroy()

        mRealm.close()
    }

//    あくまで仮Data　addTaskForTestメソッド内でRealmに仮のDataを保存　するMethod
    private fun addTaskForTest() {
        val task = Task()
        task.title = "作業"
        task.contents = "プログラムを書いてPUSHする"
        task.date = Date()
        task.id = 0
        mRealm.beginTransaction()
        mRealm.copyToRealmOrUpdate(task)
        mRealm.commitTransaction()
    }


}
//Class END