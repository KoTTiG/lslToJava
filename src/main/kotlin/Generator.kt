import org.jetbrains.research.libsl.nodes.Function
import org.jetbrains.research.libsl.nodes.references.AutomatonReference
import java.io.BufferedWriter
import java.io.File

class Generator {
     fun generateClass(packageName: String, automaton: AutomatonReference) {
        val className = automaton.name
        val packageDir = File("$createddataPath$packageName")
        packageDir.mkdirs()
        val file = File("$createddataPath$packageName/$className.java")
        val writer = file.bufferedWriter()

        val a = automaton.resolve() //

         if (a != null) {
        writer.write("package $packageName;\n")
        writer.newLine()
        writer.write("public class $className{\n")

            for (variable in a.constructorVariables){
                generateVar(variable, writer)
            }
             for (variable in a.internalVariables){
                 generateVar(variable, writer)
            }
            for (func in a.functions){
                generateFunction(func, writer)
            }

        writer.write("}")

        writer.close()
        }
    }

    fun generateVar(func: variable, writer: BufferedWriter){}


    fun generateFunction(func: Function, writer: BufferedWriter) {
        val funcName = func.name
        val args = getArgs(func)

        for (contract in func.contracts){
            //TODO()
        }

        val returnType: String = if (func.returnType == null){
            "void"
        } else {
            func.returnType!!.name
        }
        writer.write("$returnType $funcName($args) {" )
        writer.write("}\n")
    }

    fun getArgs(func: Function): String { //TODO("Запятая для разделения нескольких аргументов")
        val argString = StringBuilder()

        for (arg in func.args){
            argString.append(arg.typeReference.name + " " + arg.name)
        }
        return argString.toString()
    }
}