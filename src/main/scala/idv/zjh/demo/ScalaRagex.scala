package idv.zjh.demo



import scala.util.matching.Regex

/**
 * Scala 基礎語法示範
 */
object ScalaRagex {
  val regexUrl = "(https?://[\\w-\\.]+(:\\d+)?(\\/[~\\w\\/\\.]*)?(\\?\\S*)?(#\\S*)?)"
  val regexEmail = "([a-zA-Z0-9._%-]+@([a-zA-Z0-9.-]+))"
  val regexNumberSymbol = "([(\\w)(\\d)(/)(\\-)(\\.)]+)"
  val regexSpecialSymbol = "(\\pP|\\pS|\\s| )+"
  val regexChinese = "([\\u4E00-\\u9FFF])"
  val regexOtherSymbol = "(\\W)"
  val regex = regexUrl + "|" + regexEmail + "|" + regexNumberSymbol + "|" + regexSpecialSymbol + "|" + regexChinese + "|" + regexOtherSymbol
  val pattern = new Regex(regex)


  def main(args: Array[String]): Unit = {
//    checkSpecialSymbol()
//    checkChinese()
//    checkOtherSymbol()
//    testMatches()
    test()

  }

  var text1 = "天霞．蘭迪亞 太好了機巧少女不會懷孕？。。。圖文相符太神www我笑了xDD遇過+1隨便打一場，結束後會自動回收，與距離無關採集的喔没打玩，圣女A，姬子（b级的），那个组合好，我应该冲那个角色介紹首抽的問題在另一串就有 你去另一串健檢串發問吧好哦，谢谢綠色的1星裝直接當肥料吃 2星裝備我同樣的都留3把(艦團有時候會需要上交2星裝備 但機率不高)其餘多的2星武器>賣掉 2星聖痕>分解 3星裝備多的也都分解不好意思 想再問兩個問題EUSW9CXHM315以前   帳號等級<角色等級      15之後 等級=等級精準沒有四抽寶底 我昨天才試過@@請問現在還有標準4抽保底嗎？標準一直都有4抽保底，精準一直都是10抽樓主精準說錯4抽保底是抽一次就沒了嗎？沒錯，後來就是正常的10抽保底了以申請嚕 ID浮雲若曉不好意思 額滿嘍歐雨大佬，收嗎（hello 有在線上嗎 想當你徒弟我想要解師徒任務拿炎八碎片id:10296980好哦，我上去看看已領 感謝已領，謝謝100巴幣  = =100巴幣==WIFI抽都很歐CCCCCCCCCCCC現在哪裡可以抽她 我入坑的時候 她的池已經關了夜羽 夜羽 現在池子有開，角色武器聖痕一次抽滿我也有過 幹。臉被削到了鼻子不見啦母湯會嚇到騷1星聖痕也是吃掉嗎另外就是該先升平民畢業裝還是聖痕?我講裝備=武器+聖痕 綠色的1星全吃沒問題你是說先升武器還聖痕嗎 這個我覺得要看是什麼決定欸 不過通常都是武器優先拉 你可以貼你的裝備圖讓我看看好的沒問題 還有我有打算買月卡就是了千萬別挖要體力的  挖免費的就好了體力有多可以先拿去刷碎片CP超低呀 拿去刷黑蛋或碎片凱旋讚 但升上S比較好用想請問一下噢現在抽巫女跟勿忘的活動結束了以後還有辦法抽這兩隻女武神嗎?EN不好意思，我好像收到別人了，剛剛比對ID才發現不同sor沒關係 我也有別人收我了謝謝拉看您好友已滿…無法加，我的id：10041857我+你了，不好意思你要申請我當導師喔，麻煩你了已申請，請確認~我還能直接給3000勒，還100==收到啦 謝謝你被領過了…對 剛剛滿心期待的要去領結果被領了真的很乾不過還是謝謝樓主無私的分享 已領已領 謝謝已領 感謝檸檬貓 我看錯了 我是陸服 只能等他復刻了QQ挺ㄋㄟㄋㄟ"
  var text2 = "採用展開法計算勝率，若沒算錯應該還算可靠，展開法的優點是可以輕鬆分析勝敗因素100-(23-12)=89            89-(23-12)=78            78-(16-12)*7=50            50-(23-12)=39                39-(23-12)=28            28-(16-12)*7=WIN100-floor((23-12)*1.25)=87    87-floor((23-12)*1.25)=74    74-floor((16-12)*1.25)*7=39    39-floor((23-12)*1.25)=26        26-floor((23-12)*1.25)=13    13-floor((16-12)*1.25)*7=WIN渡鴉有沒有發動攻擊上升似乎沒差若德傻六回合內未擊敗渡鴉，則渡鴉必勝100-(19-14)=95        95-(19-14)=90        90-(16-14)*5=80        80-(19-14)=75        75-(19-14)=70        70-(16-14)*5=60                                                75-(19-14)=70 14-5=9    70-(16-9)*5=35                                    80-(19-14)=75 14-5=9    75-(19-9)=65        65-(16-9)*5=30                                                75-(19-9)=65 9-5=4    65-(16-4)*5=5                        90-(16-14)*5=80    14-5=9    80-(19-9)=70        70-(19-9)=60        60-(16-9)*5=25                                                70-(19-9)=60 9-5=4    60-(16-4)*5=WIN    7.497% 第三四五回合內至少發動降防2次時必勝                                    80-(19-9)=70 9-5=4    70-(19-4)=55        55-(16-4)*5=WIN                                                70-(19-4)=55 4-5=0    55-(16-0)*5=WIN            95-(19-14)=90 14-5=9    90-(16-9)*5=55        55-(19-9)=45        45-(19-9)=35        35-(16-9)*5=WIN    21% 第二回合發動降防必勝                                                45-(19-9)=35 4-5=0    35-(16-0)*5=WIN                                    55-(19-9)=45 9-5=4    45-(19-4)=30        30-(16-4)*5=WIN                                                45-(19-4)=30 4-5=0    25-(16-0)*5=WIN                        90-(16-9)*5=55 9-5=4    55-(19-4)=40        40-(19-4)=25        25-(16-4)*5=WIN                                                40-(19-4)=25 4-5=0    25-(16-0)*5=WIN                                    55-(19-4)=40 4-5=0    40-(19-0)=21        21-(16-0)*5=WIN100-(19-14)=95 14-5=9    95-(19-9)=85        85-(16-9)*5=50        50-(19-9)=40        40-(19-9)=30        30-(16-9)*5=WIN    30% 第一回合發動降防必勝                                                40-(19-9)=30 9-5=4    30-(16-4)*5=WIN                                    50-(19-9)=40 9-5=4    40-(19-4)=25        25-(16-4)*5=WIN                                                40-(19-4)=25 9-5=4    25-(16-4)*5=WIN                        85-(16-9)*5=50 9-5=4    50-(19-4)=35        35-(19-4)=20        20-(16-4)*5=WIN                                                35-(19-4)=20 4-5=0    20-(16-0)*5=WIN                                    50-(19-4)=35 4-5=0    35-(19-0)=16        16-(16-0)*5=WIN            95-(19-9)=85 9-5=4    85-(16-4)*5=25        25-(19-4)=10        10-(19-4)=WIN                                    25-(19-4)=10 4-5=0    10-(19-0)=WIN                        85-(16-4)*5=25 4-5=0    25-(19-0)=6        6-(19-0)=WIN前一二回合內發動降防德傻必勝第三四五回合內至少降防2次時德傻必勝勝率：渡鴉: 41.503% | 德傻: 58.497%期望：渡鴉: 2.07515 | 德傻: 1.930401不想花時間檢查錯誤，睡"
  var text3 = "第一次在戰場打流程，很多失誤，而且好累連結：https://youtu.be/4yqFW7QjEyg來說上分支追擊的部分，原本一開始是先上再打普攻追擊，但常常打完分支後要追皮皮馬讓我輸出的很痛苦，所以影片中才決定放大招前再上分之追擊話雖如此，後面還是有失誤沒上到就開大招:P "
  var text4 = "這坨東西...前幾天看到大佬地圖裝幻海跟殺神一樣3萬5、3萬6的，於是我也試了一把....嗯我果然是個凡人...而且還很菜qwq怎麼蹭個閃避都會蹭歪呢....-__-第一次用希兒打戰場有夠慘烈.....再多多加油連結：https://youtu.be/7ntpTqxs0Dg聖女 起源套(沒其他輔助裝了)幻海 愛神鐮刀 希兒 荼靡 魔王山吹  特斯拉樂隊 奧提爾之後用女王試試也只打了3萬1.....嗯/_>\\"
  def test():Unit={
    var str = text4

    val pattern = new Regex(regex)
    var s = str.split("[,|。|，|：|!|、|？|　| | ]").map(_.trim).filter(T =>{
      T.length > 0
    }).map(text => {
      // 將文字切成一個一個字元，並將網址、數字、英文等視為一個字元
      (pattern findAllIn text).toList
    })

    s.foreach(T=>{
      println(T)
    })
    println(s.length)

    println("---------------------------分隔符---------------------------------------")
    var s2 = str.split("\\pP|\\pS|\\s|　").map(_.trim)
    s2.foreach(T=>{
      println(T)
    })

    println(s2.length)
//    println((pattern findAllIn str).mkString(" ||| "))
  }

  def testMatches():Unit={
    var str = "採用展開法計算勝率，若沒算錯應該還算可靠，展開法的優點是可以輕鬆分析勝敗因素100-(23-12)=89            89-(23-12)=78            78-(16-12)*7=50            50-(23-12)=39                39-(23-12)=28            28-(16-12)*7=WIN100-floor((23-12)*1.25)=87    87-floor((23-12)*1.25)=74    74-floor((16-12)*1.25)*7=39    39-floor((23-12)*1.25)=26        26-floor((23-12)*1.25)=13    13-floor((16-12)*1.25)*7=WIN渡鴉有沒有發動攻擊上升似乎沒差若德傻六回合內未擊敗渡鴉，則渡鴉必勝100-(19-14)=95        95-(19-14)=90        90-(16-14)*5=80        80-(19-14)=75        75-(19-14)=70        70-(16-14)*5=60                                                75-(19-14)=70 14-5=9    70-(16-9)*5=35                                    80-(19-14)=75 14-5=9    75-(19-9)=65        65-(16-9)*5=30                                                75-(19-9)=65 9-5=4    65-(16-4)*5=5                        90-(16-14)*5=80    14-5=9    80-(19-9)=70        70-(19-9)=60        60-(16-9)*5=25                                                70-(19-9)=60 9-5=4    60-(16-4)*5=WIN    7.497% 第三四五回合內至少發動降防2次時必勝                                    80-(19-9)=70 9-5=4    70-(19-4)=55        55-(16-4)*5=WIN                                                70-(19-4)=55 4-5=0    55-(16-0)*5=WIN            95-(19-14)=90 14-5=9    90-(16-9)*5=55        55-(19-9)=45        45-(19-9)=35        35-(16-9)*5=WIN    21% 第二回合發動降防必勝                                                45-(19-9)=35 4-5=0    35-(16-0)*5=WIN                                    55-(19-9)=45 9-5=4    45-(19-4)=30        30-(16-4)*5=WIN                                                45-(19-4)=30 4-5=0    25-(16-0)*5=WIN                        90-(16-9)*5=55 9-5=4    55-(19-4)=40        40-(19-4)=25        25-(16-4)*5=WIN                                                40-(19-4)=25 4-5=0    25-(16-0)*5=WIN                                    55-(19-4)=40 4-5=0    40-(19-0)=21        21-(16-0)*5=WIN100-(19-14)=95 14-5=9    95-(19-9)=85        85-(16-9)*5=50        50-(19-9)=40        40-(19-9)=30        30-(16-9)*5=WIN    30% 第一回合發動降防必勝                                                40-(19-9)=30 9-5=4    30-(16-4)*5=WIN                                    50-(19-9)=40 9-5=4    40-(19-4)=25        25-(16-4)*5=WIN                                                40-(19-4)=25 9-5=4    25-(16-4)*5=WIN                        85-(16-9)*5=50 9-5=4    50-(19-4)=35        35-(19-4)=20        20-(16-4)*5=WIN                                                35-(19-4)=20 4-5=0    20-(16-0)*5=WIN                                    50-(19-4)=35 4-5=0    35-(19-0)=16        16-(16-0)*5=WIN            95-(19-9)=85 9-5=4    85-(16-4)*5=25        25-(19-4)=10        10-(19-4)=WIN                                    25-(19-4)=10 4-5=0    10-(19-0)=WIN                        85-(16-4)*5=25 4-5=0    25-(19-0)=6        6-(19-0)=WIN前一二回合內發動降防德傻必勝第三四五回合內至少降防2次時德傻必勝勝率：渡鴉: 41.503% | 德傻: 58.497%期望：渡鴉: 2.07515 | 德傻: 1.930401不想花時間檢查錯誤，睡"
    val result = str.matches(regex)
    println(result)
  }

  /**
   * 網頁檢查 運算符
   */
  def checkUrl(): Unit ={
    val regexUrl = "(https?://[\\w-\\.]+(:\\d+)?(\\/[~\\w\\/\\.]*)?(\\?\\S*)?(#\\S*)?)"
    val str = "http://tw.gitb/ook.net/scala/scala_regular_expressions.html我https://www.facebook.com/ https://www.youtube.com/watch?v=EABd74ruNQw"
    val pattern = new Regex(regexUrl)
    println((pattern findAllIn str).mkString(" ||| "))
  }

  /**
   * Email 運算符
   */
  def checkEmail(): Unit ={
    val regexEmail = "([a-zA-Z0-9._%-]+@([a-zA-Z0-9.-]+))"
    val pattern = new Regex(regexEmail)
    var str = "b195fea@163.comddddd.dfgds gsd"
    println((pattern findAllIn str).mkString(" ||| "))
  }

  /**
   * 各類英文數字符號(- . /)組合 109.2.5 ||| 109-2-5 ||| 103/2/5 ||| 2-9
   */
  def checkNumberSymbol(): Unit ={
    val regexNumberSymbol = "([(\\w)(\\d)(/)(\\-)(\\.)]+)"
    val pattern = new Regex(regexNumberSymbol)
    var str = "109.2.5 109-2-5 103/2/5 2-9 AE5567，89E7 dgfdafsgh,6516 sgsdfgd456 5566"
    println((pattern findAllIn str).mkString(" ||| "))
  }

  /**
   * 特殊符號編輯（逗號、句號空白等分隔符）
   */
  def checkSpecialSymbol(): Unit ={
    val regexSpecialSymbol = "(\\pP|\\pS|\\s| )+"
    val pattern = new Regex(regexSpecialSymbol)
    var str = ",dagdag,,A==\"Aﾟдﾟ，，fsg.s。fg，  ，s dg"
    println((pattern findAllIn str).mkString(" ||| "))
  }

  /**
   * 切割所有中日韓字符
   */
  def checkChinese(): Unit ={
    val regexChinese = "([\\u4E00-\\u9FFF])"
    val pattern = new Regex(regexChinese)
    var str = "鍾嘉豪你好ﾟдﾟ"
    println((pattern findAllIn str).mkString(" ||| "))
  }
  /**
   * 所有非單字字符
   */
  def checkOtherSymbol(): Unit ={
    val regexOtherSymbol = "([\\W])"
    val pattern = new Regex(regexOtherSymbol)
    var str = "，，sgsfdg，，鍾嘉d255豪，，aaa，羅莎莉亞很可愛ﾟдﾟ，不知道是羅莎莉亞比較可愛，還是莉莉婭比較可愛"
    println((pattern findAllIn str).mkString(" ||| "))
  }
}
