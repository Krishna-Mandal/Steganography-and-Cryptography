package cryptography

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Exception
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
            "show" -> println("Obtaining message from image.")
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

    val outImageFile = File(outImage)
    val bufferedImageOut = BufferedImage(bufferedImageIn.width, bufferedImageIn.height, BufferedImage.TYPE_INT_RGB)

    for (x in 0 until bufferedImageIn.width) {
        for (y in 0 until bufferedImageIn.height) {
            val color = Color(bufferedImageIn.getRGB(x, y))
            val newColor = Color(
                (color.red or 0x01),
                (color.green or 0x01),
                (color.blue or 0x01)
            )
            bufferedImageOut.setRGB(x, y, newColor.rgb)
        }
    }
    try {
        ImageIO.write(bufferedImageOut, "png", outImageFile)
        println("Image $outImage is saved.")
    } catch (ex: Exception) {
        println("Can't write output file!")
        return
    }

}



