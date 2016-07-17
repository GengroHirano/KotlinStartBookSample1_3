/**
 * Kotlinスタートブック第三章までやってる。
 */

package sample

fun main(args: Array<String>) {
    println("Hello, World")
    val half = Rational(7, 14)
    val value = half + Rational(1, 3)


    println(half.denominator)
    println(half)
    println(value)
    println(3 + Rational(1, 3))
}

//<editor-fold desc="こんなたたみ方も有るよ">
//class Rational(val numerator: Int, val denominator: Int){
//    //イニシャライザ
//    init {
//        require(denominator != 0, {"denominator must not be null"})
//    }
//
//    //符号の右側の式がメソッドの戻り値になる。
//    override fun toString(): String = "${numerator}/${denominator}"
//}
//</editor-fold>

//訳文の追加
//プライマリコンストラクタのデフォルトはval
class Rational(n: Int, d: Int){
    //イニシャライザ
    init {
        require(d != 0, {"denominator must not be null"})
    }

    private val g by lazy { gcd(Math.abs(n), Math.abs(d)) }
    val numerator: Int = n / g
    val denominator: Int = d / g

    //符号の右側の式がメソッドの戻り値になる。
    override fun toString(): String = "${numerator}/${denominator}"
//    <editor-fold desc="description">
//    pulus rational 2 / 1 + 1 / 3
//    2 * 3 + 1 * 1 = 7 / 1 * 3 = 3
//    7 / 3
//    </editor-fold>
//    演算子オーバロード operator 修飾子を用いる
    operator fun plus(that: Rational): Rational =
        Rational(
                numerator * that.denominator + denominator * that.numerator,
                denominator * that.denominator
        )

//    <editor-fold desc="description">
//    1 / 2 + 1 / 1
//    1 + 1 * 2 = 3 / 2
//    3 / 2
//    </editor-fold>
//    メソッドのオーバーロード
    operator fun plus(n: Int): Rational =
        Rational(numerator + n * denominator, denominator)

    //tailrec は再起を最適化してくれる
    tailrec private fun gcd(a: Int, b: Int): Int =
            if (b == 0) a
            else gcd(b, a % b)
}

//拡張関数
//<editor-fold desc="俺のためのメモ @交換法則">
//掛ける数と掛けられる数を入れ替えても積は変わらない法則のこと
//だからこの処理はRationalで定義している演算子オーバーロードされたメソッドを用いて処理をしている
//</editor-fold>
operator fun Int.plus(r: Rational): Rational = r + this