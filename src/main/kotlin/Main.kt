import org.jetbrains.research.libsl.LibSL
import org.jetbrains.research.libsl.nodes.Function
import org.jetbrains.research.libsl.nodes.references.AutomatonReference
import java.io.BufferedWriter
import java.io.File
import Generator


const val baseRoot = "./src/test"
const val testdataPath = "$baseRoot/testdata/"
const val createddataPath = "$baseRoot/createddata/"


fun main(args: Array<String>) {
    val libsl = LibSL(testdataPath + "lsl/")
    val library = libsl.loadFromFileName("types.lsl")
    val generator = Generator()
    val name = library.metadata.name
    for (automaton in library.automataReferences){
        generator.generateClass(name, automaton)
    }
}










