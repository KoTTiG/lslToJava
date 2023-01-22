import org.jetbrains.research.libsl.nodes.*
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

            /*for (variable in a.constructorVariables){
                generateVar(variable, writer)
            }*/
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

    fun generateVar(variable: VariableWithInitialValue, writer: BufferedWriter){
        writer.write(variable.typeReference.name + " " + variable.name + ";\n")

    }

    fun generateContract(contract: Contract): String {
        val string = StringBuilder()
        string.append(contract.expression.dumpToString())

        return(string.toString())


    }


    fun generateFunction(func: Function, writer: BufferedWriter) {
        val funcName = func.name
        val args = getArgs(func)



        val returnType: String = if (func.returnType == null){
            "void"
        } else {
            func.returnType!!.name
        }
        writer.write("$returnType $funcName($args) {\n" )
        writer.newLine()
        writer.write("//REQUIRES\n")
        for (contract in func.contracts.filter { it.kind == ContractKind.REQUIRES }){

            val contractString = generateContract(contract)
            writer.write("if $contractString {}\n")

        }
        writer.newLine()
        writer.write("//function body \n") //TODO()
        writer.newLine()
        writer.write("//ENSURES\n")
        for (contract in func.contracts.filter { it.kind == ContractKind.ENSURES }){
            val contractString = generateContract(contract)
            writer.write("if $contractString {}\n")
            //TODO("хранение значений до вызова функции")

        }


        writer.write("}\n")
    }

    fun getArgs(func: Function): String { //TODO("Запятая для разделения нескольких аргументов")
        val argString = StringBuilder()

        for (arg in func.args){
            argString.append(arg.typeReference.name + " " + arg.name + ", ")
        }
        argString.delete((argString.length - 2), (argString.length))
        return argString.toString()
    }
}