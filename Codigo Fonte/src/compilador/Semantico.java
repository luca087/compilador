package compilador;

import java.util.*;

public class Semantico implements Constants {
    
   //registros semanticos
   private StringBuilder  codigoObjeto = new StringBuilder();
   private Stack<String>  pilhaTipos = new Stack<>();
   private String operadorRelacional = "";
   private String tipo = "";
   private Stack<String> pilhaRotulos = new Stack<>();
   private List<String> listaIdentificadores = new ArrayList<>();
   private Dictionary<String, String> tabelaSimbolos = new Hashtable<>();


   public void executeAction(int action, Token token) throws SemanticError {
      System.out.println("Acao #"+action+", Token: "+token);

      switch (action) {
         case  1:  acao1(); break;
         case  2:  acao2(); break;
         case  3:  acao3(); break;
         case  4:  acao4(); break;
         case  5:  acao5(token); break;
         case  6:  acao6(token); break;
         case  7:  acao7(); break;
         case  8:  acao8(); break;
         case  9:  acao9(token); break;
         case  10:  acao10(); break;
         case 11: acao11(); break;
         case 12: acao12(); break;
         case 13: acao13(); break;
         case 14: acao14(); break;
         case  15:  acao15(); break;
         case  16:  acao16(); break;
         case  17:  acao17(); break;
         case  18:  acao18(); break;
         case  19:  acao19(); break;
         case 20: acao20(); break;
         case 21: acao21(); break;
          case  22:  acao22(token); break;
          case  23:  acao23(); break;
          case  24:  acao24(token); break;
          case 25: acao25(); break;
          case 26: acao26(token); break;
          case  27:  acao27(); break;
          case 28: acao28(); break;
          case 29: acao29(); break;
          case  30:  acao30(); break;
          case  31:  acao31(); break;
          case  32:  acao32(); break;
          case 33: acao33(); break;
          case 34: acao34(); break;
         default:
                  throw new SemanticError("Acao semantica nao implementada: " + action);
      }
   }

    private void acao34() {
        var tipo1 = pilhaTipos.pop();
        var rotulo1 = pilhaRotulos.pop();

        //WIP
    }

    private void acao33() {
        var tipo1 = pilhaTipos.pop();
        var rotulo1 = pilhaRotulos.pop();

        //WIP
    }

    private void acao32() {
        //WIP
    }

    private void acao31() {

    }

    private void acao30() {
        var tipo1 = pilhaTipos.pop();

        //WIP
    }

    private void acao29() {
        var rotulo1 = pilhaRotulos.pop();

        //WIP
    }

    private void acao28() {
        var rotulo1 = pilhaRotulos.pop();
        var rotulo2 = pilhaRotulos.pop();

        //WIP

        pilhaRotulos.push(rotulo2);

        //WIP
    }

    private void acao27() {
        var tipo1 = pilhaTipos.pop();

    }

    private void acao26(Token token) {
        //WIP
    }

    private void acao25() {
        var tipo1 = pilhaTipos.pop();

        //WIP
    }

    private void acao24(Token token) {
        listaIdentificadores.add(token.getLexeme());
    }

    private void acao23() {
        //WIP
    }

    private void acao22(Token token) {
        tipo = token.getLexeme();
    }

    private void acao19() {
        pilhaTipos.push("string");

        //WIP
    }

    private void acao18() {
       pilhaTipos.push("char");

       //WIP
    }

    private void acao17() {
        var tipo1 = pilhaTipos.pop();
        var tipo2 = pilhaTipos.pop();

        pilhaTipos.push("int64");

        //WIP
    }

    private void acao16() {
        var tipo1 = pilhaTipos.pop();
        var tipo2 = pilhaTipos.pop();

        pilhaTipos.push("bool");

        //WIP
    }

    private void acao15() {
        var tipo1 = pilhaTipos.pop();
        var tipo2 = pilhaTipos.pop();

        pilhaTipos.push("bool");

        //WIP
    }

    private void acao10() {
        var tipo1 = pilhaTipos.pop();
        var tipo2 = pilhaTipos.pop();

        //WIP
    }

    private void acao9(Token token) {
        operadorRelacional = token.getLexeme();
    }

    private void acao1() {
      String tipo1 = pilhaTipos.pop();
      String tipo2 = pilhaTipos.pop();
      if ("int64".equals(tipo1) && "int64".equals(tipo2)) {
         pilhaTipos.push("int64");
      } else {
         pilhaTipos.push("float64");
      }
      codigoObjeto.append("add\n");
   }

   private void acao2() {
      String tipo1 = pilhaTipos.pop();
      String tipo2 = pilhaTipos.pop();
      if ("int64".equals(tipo1) && "int64".equals(tipo2)) {
         pilhaTipos.push("int64");
      } else {
         pilhaTipos.push("float64");
      }
      codigoObjeto.append("sub\n");
   }

   private void acao3() {
      String tipo1 = pilhaTipos.pop();
      String tipo2 = pilhaTipos.pop();
      if ("int64".equals(tipo1) && "int64".equals(tipo2)) {
         pilhaTipos.push("int64");
      } else {
         pilhaTipos.push("float64");
      }
      codigoObjeto.append("mul\n");
   }

   private void acao4() {
      String tipo1 = pilhaTipos.pop();
      String tipo2 = pilhaTipos.pop();
      pilhaTipos.push("float64");
      codigoObjeto.append("div\n");
   }

   private void acao5(Token token) {
     pilhaTipos.push("int64");
     codigoObjeto.append("ldc.i8 " + token.getLexeme() + "\n");
     codigoObjeto.append("conv.r8\n");
   }

   private void acao6(Token token) {
      pilhaTipos.push("float64");
      codigoObjeto.append("ldc.r8 " + token.getLexeme() + "\n");
   }
   
   private void acao7() {
      String tipo = pilhaTipos.pop();
      if ("int64".equals(tipo)) {
         pilhaTipos.push("int64");
      } else {
         pilhaTipos.push("float64");
      }	  
   }     

   private void acao8() {
      String tipo = pilhaTipos.pop();
      if ("int64".equals(tipo)) {
         pilhaTipos.push("int64");
      } else {
         pilhaTipos.push("float64");
      }	
      codigoObjeto.append("ldc.i8 -1\n");
      codigoObjeto.append("conv.r8\n");
      codigoObjeto.append("mul\n");
   }
	
   private void acao11() {
      pilhaTipos.push("bool");
      codigoObjeto.append("ldc.i4.1\n");
   }

   private void acao12() {
      pilhaTipos.push("bool");
      codigoObjeto.append("ldc.i4.0\n");
   }

   private void acao13() {
      String tipo = pilhaTipos.pop();
      pilhaTipos.push("bool");
      codigoObjeto.append("ldc.i4.1\n");
      codigoObjeto.append("xor\n");
   }

   private void acao14() {
      String tipo = pilhaTipos.pop();
      if ("int64".equals(tipo)) {
         codigoObjeto.append("conv.i8\n");
      }
      codigoObjeto.append("call void [mscorlib]System.Console::Write(" + tipo + ")\n");
    }
	
   private void acao20() {
      codigoObjeto.append(".assembly extern mscorlib {}\n");
      codigoObjeto.append(".assembly _programa{}\n");
      codigoObjeto.append(".module _programa.exe\n");
      codigoObjeto.append("\n");
      codigoObjeto.append(".class public _unica{\n");
      codigoObjeto.append(".method static public void _principal(){\n");
      codigoObjeto.append(".entrypoint\n");
   }

   private void acao21() {
      codigoObjeto.append("ret\n");
      codigoObjeto.append("}\n");
      codigoObjeto.append("}");
   }

   public String getCodigoObjeto() {
      return codigoObjeto.toString();
   }
}