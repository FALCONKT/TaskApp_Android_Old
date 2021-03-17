package jp.techacademy.kenji.takada.taskapp


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

//Interface読み込み　手入力
import java.io.Serializable

//Serializable はInterfase
open class Task : RealmObject(), Serializable {

//open class Task  : RealmObject(),Serializable {
    var title: String = ""      // タイトル
    var contents: String = ""   // 内容
    var date: Date = Date()     // 日時

    // id を優先KEYとして設定
    @PrimaryKey
    var id: Int = 0
}