package cryptography

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Integer.toBinaryString
import javax.imageio.ImageIO


fun main() {
    simpleEncryptDemo()
}

fun simpleEncryptDemo() {
    while (true) {
        println("Task (hide, show, exit):")
        when(readln()) {
            "exit" -> {
                println("Bye!")
                break }
            "hide" -> hide()
            "show" -> show()
            else -> println("Wrong task: [input String]")
        }
    }
}

fun hide() {
    val bufferedImageIn: BufferedImage
    println("Input image file:")
    val inImage = readln()

    println("Output image file:")
    val outImage = readln()

    try {
        bufferedImageIn = ImageIO.read(File(inImage))
    } catch (exec: Exception) {
        println("Can't read input file!")
        return
    }

    println("Input Image: $inImage")
    println("Output Image: $outImage")

    println("Message to hide:")
    val msg = readln()
    val imgSize = bufferedImageIn.width * bufferedImageIn.height
    val msgBits = mutableListOf<String>()
    val msgByte = "$msg\u0000\u0000\u0003".toByteArray()
    msgByte.forEach { String.format("%8s", toBinaryString(it.toInt())).replace(' ', '0').forEach { bit -> msgBits.add(bit.toString()) } }

    if(imgSize < msgBits.size) {
        println("The input image is not large enough to hold this message.")
        return
    }

    val outImageFile = File(outImage)
    val bufferedImageOut = BufferedImage(bufferedImageIn.width, bufferedImageIn.height, BufferedImage.TYPE_INT_RGB)

    var msgIndex = 0
    for (y in 0 until bufferedImageIn.height) {
        for (x in 0 until bufferedImageIn.width) {
            val color = Color(bufferedImageIn.getRGB(x, y))
            var newBlue = color.blue
            if (msgIndex < msgBits.size) {
                if (color.blue % 2 == 0) { if (msgBits[msgIndex].toInt() == 1) newBlue = color.blue or 1 }
                else { if (msgBits[msgIndex].toInt() == 0) newBlue = (color.blue xor 1) }
            }
            val newColor = Color(
                (color.red),
                (color.green),
                (newBlue)
            )
            bufferedImageOut.setRGB(x, y, newColor.rgb)
            msgIndex++
        }
    }
    try {
        ImageIO.write(bufferedImageOut, "png", outImageFile)
        println("Message saved in $outImage image.")
    } catch (ex: Exception) {
        println("Can't write output file!")
        return
    }
}

fun show() {
    println("Input image file:")

    val inImage = readln()
    val inBufferedImage = ImageIO.read(File(inImage))
    val msgArray = mutableListOf<Int>()

    for (y in 0 until inBufferedImage.height) {
        for (x in 0 until inBufferedImage.width) {
            val color = Color(inBufferedImage.getRGB(x, y))
            val lastBit = color.blue.toString(2).takeLast(1).toInt()
            msgArray += lastBit
        }
    }
    println("Message:")
    println(msgArray.joinToString("").split("000000000000000000000011").first().chunked(8).forEach { print(it.toInt(2).toChar()) })

}



