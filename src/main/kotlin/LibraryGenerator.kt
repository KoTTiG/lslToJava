import org.jetbrains.research.libsl.nodes.Automaton
import org.jetbrains.research.libsl.nodes.Library

class LibraryGenerator (private val lslLibrary: Library){

    private val name = lslLibrary.metadata.name
    private val classes = mutableListOf<Automaton>()

    private fun getClasses() {
        for (aRef in lslLibrary.automataReferences){
            aRef.resolve()?.let { classes.add(it) }
        }
    }




}