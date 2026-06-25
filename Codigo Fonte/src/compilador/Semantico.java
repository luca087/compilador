package compilador;

import java.util.*;

public class Semantico implements Constants {
    
   //registros semanticos
   private StringBuilder  codigoObjeto = new StringBuilder();
   private Stack<String>  pilhaTipos = new Stack<>();
   private String operadorRelacional = "";
   private String tipo = "";
   private Stack<String> pilhaRotulos = new Stack<>();
   private int indiceRotulo = 0;
   private List<String> listaIdentificadores = new ArrayList<>();
   private Dictionary<String, String> tabelaSimbolos = new Hashtable<>();


   public void executeAction(int action, Token token) throws SemanticError {
      try {
          System.out.println("Acao #" + action + ", Token: " + token);

          switch (action) {
              case 1:
                  acao1();
                  break;
              case 2:
                  acao2();
                  break;
              case 3:
                  acao3();
                  break;
              case 4:
                  acao4();
                  break;
              case 5:
                  acao5(token);
                  break;
              case 6:
                  acao6(token);
                  break;
              case 7:
                  acao7();
                  break;
              case 8:
                  acao8();
                  break;
              case 9:
                  acao9(token);
                  break;
              case 10:
                  acao10();
                  break;
              case 11:
                  acao11();
                  break;
              case 12:
                  acao12();
                  break;
              case 13:
                  acao13();
                  break;
              case 14:
                  acao14();
                  break;
              case 15:
                  acao15();
                  break;
              case 16:
                  acao16();
                  break;
              case 17:
                  acao17();
                  break;
              case 18:
                  acao18(token);
                  break;
              case 19:
                  acao19(token);
                  break;
              case 20:
                  acao20();
                  break;
              case 21:
                  acao21();
                  break;
              case 22:
                  acao22(token);
                  break;
              case 23:
                  acao23();
                  break;
              case 24:
                  acao24(token);
                  break;
              case 25:
                  acao25();
                  break;
              case 26:
                  acao26(token);
                  break;
              case 27:
                  acao27();
                  break;
              case 28:
                  acao28();
                  break;
              case 29:
                  acao29();
                  break;
              case 30:
                  acao30();
                  break;
              case 31:
                  acao31(token);
                  break;
              case 32:
                  acao32();
                  break;
              case 33:
                  acao33(token);
                  break;
              case 34:
                  acao34();
                  break;
              default:
                  throw new SemanticError("Acao semantica nao implementada: " + action);
          }
      }catch(SemanticError se){
          throw  se;
      }
      catch(Exception e)
      {
        throw  new SemanticError("Erro semântico!");
       }
   }

    private void acao34() {
        var tipo1 = pilhaTipos.pop();
        var rotulo1 = pilhaRotulos.pop();


        codigoObjeto.append("brfalse "+rotulo1+"\n");
        //WIP
    }

    ///
    /// TRABALHO FINAL - parte 4: implementação de compatibilidade de tipos
    /// A <expressao> de comandos de <seleção> e de <repetição> devem ser do tipo bool.
    /// EQUIPE 13: implemente a compatibilidade de tipos da ação #33, de tal forma que se a <expressao> da cláusula
    /// while não for do tipo compatível deve-se encerrar a execução e apontar erro semântico, indicando a linha e
    /// apresentando a mensagem "tipo incompatível em comando de repetição <repeat-while>"
    ///

    private void acao33(Token token) throws SemanticError {
        var tipo1 = pilhaTipos.pop();
        if(!tipo1.equals("bool")){
            throw new SemanticError("tipo incompatível em comando de repetição <repeat-while>", token.getPosition());
        }
        var rotulo1 = pilhaRotulos.pop();

        codigoObjeto.append("brtrue "+rotulo1+"\n");
        //WIP
    }

    private void acao32() {
        var rotulo = "rotulo_"+indiceRotulo++;
        pilhaRotulos.push(rotulo);
        codigoObjeto.append(rotulo+":\n");
        //WIP
    }

    private void acao31(Token token) {
        pilhaTipos.push(tipo);


    }

    private void acao30() {
        var tipo1 = pilhaTipos.pop();

        var rotulo = "rotulo_"+indiceRotulo++;
        pilhaRotulos.push(rotulo);
        codigoObjeto.append("brfalse "+rotulo+"\n");
        //WIP
    }

    private void acao29() {
        var rotulo1 = pilhaRotulos.pop();

        codigoObjeto.append(rotulo1+":\n");
        //WIP
    }

    private void acao28() {
        var rotulo1 = pilhaRotulos.pop();
        var rotulo2 = pilhaRotulos.pop();

        codigoObjeto.append("br "+rotulo2+"\n");
        //WIP

        pilhaRotulos.push(rotulo2);
        codigoObjeto.append(rotulo1+":\n");
        //WIP
    }

    private void acao27() {
        var tipo1 = pilhaTipos.pop();

        pilhaRotulos.push("rotulo_"+indiceRotulo++);
        pilhaRotulos.push("rotulo_"+indiceRotulo++);

    }

    private void acao26(Token token) throws SemanticError {
        var id = token.getLexeme();
        if(tipo.equals("bool") || tipo.equals("char")){
            throw new SemanticError(id+" - identificador inválido para comando de entrada", token.getPosition());
        }
        codigoObjeto.append("call string\n");
        codigoObjeto.append("[mscorlib]System.Console::ReadLine()\n");
        codigoObjeto.append("call "+tipo+"\n");
        String className;
        if(tipo.equals("int64")){
            className = "Int64";
        }else{
            className = "Float64";
        }
        codigoObjeto.append("[mscorlib]System."+className+"::Parse(string)\n");
        codigoObjeto.append("stloc "+id+"\n");

        //WIP
    }

    private void acao25() {
        var tipo1 = pilhaTipos.pop();

        if(tipo1 == "int64"){
            codigoObjeto.append("conv.i8");
        }
        for (int i = 0; i < listaIdentificadores.size() -1; i++){
            codigoObjeto.append("dup\n");
        }

        listaIdentificadores.forEach((id)->{
            codigoObjeto.append("stloc "+id+"\n");
        });

        listaIdentificadores.clear();
    }

    private void acao24(Token token) {

        listaIdentificadores.add(token.getLexeme());
    }



    private void acao23() {
        listaIdentificadores.forEach((id)->{
            var type = getILType(tipo);
            tabelaSimbolos.put(type, id);
            codigoObjeto.append(".locals("+type+" "+id+")\n");
        });

        listaIdentificadores.clear();
    }

    private String getILType(String type){
        switch (type){
            case "int":
                return "int64";
            case "float":
                return "float64";
            default:
                return type;
        }
    }

    private void acao22(Token token) {

        tipo = token.getLexeme();
    }

    private void acao19(Token token) {
        pilhaTipos.push("string");
        codigoObjeto.append("ldcstr "+token.getLexeme());
        //WIP
    }

    private void acao18(Token token) {
       pilhaTipos.push("char");

        codigoObjeto.append("ldc.i4 "+(int)token.getLexeme().charAt(0));
       //WIP
    }

    private void acao17() {
        var tipo1 = pilhaTipos.pop();
        var tipo2 = pilhaTipos.pop();

        pilhaTipos.push("int64");

        codigoObjeto.append("[mscorlib]System.Math::Pow(float64,float64)\n");
        //WIP
    }

    private void acao16() {
        var tipo1 = pilhaTipos.pop();
        var tipo2 = pilhaTipos.pop();

        pilhaTipos.push("bool");

        codigoObjeto.append("add\n");
        codigoObjeto.append("ldc.i4 0\n");
        codigoObjeto.append("cgt\n");
        //WIP
    }

    private void acao15() {
        var tipo1 = pilhaTipos.pop();
        var tipo2 = pilhaTipos.pop();

        pilhaTipos.push("bool");

        codigoObjeto.append("add\n");
        codigoObjeto.append("ldc.i4 2\n");
        codigoObjeto.append("ceq\n");
        //WIP
    }

    private void acao10() {
        var tipo1 = pilhaTipos.pop();
        var tipo2 = pilhaTipos.pop();

        pilhaTipos.push("bool");

        switch (operadorRelacional){
            case ">":
                codigoObjeto.append("cgt\n");
                break;
            case ">=":
                codigoObjeto.append("clt\n");
                codigoObjeto.append("ldc.i4 0\n");
                codigoObjeto.append("ceq\n");
                break;
            case "<":
                codigoObjeto.append("clt\n");
                break;
            case "<=":
                codigoObjeto.append("cgt\n");
                codigoObjeto.append("ldc.i4 0\n");
                codigoObjeto.append("ceq\n");
                break;
            case "==":
                codigoObjeto.append("ceq\n");
                break;
            case "!=":
                codigoObjeto.append("ceq\n");
                codigoObjeto.append("ldc.i4 0\n");
                codigoObjeto.append("ceq\n");
                break;
        }

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