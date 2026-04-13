package compilador;

public class Lexico implements Constants
{
    private int position;
    private String input;

    public Lexico()
    {
        this("");
    }

    public Lexico(String input)
    {
        setInput(input);
    }

    public void setInput(String input)
    {
        this.input = input;
        setPosition(0);
    }

    public void setPosition(int pos)
    {
        position = pos;
    }

    private int getLine(int pos) {
        int linha = 1;
        for (int i = 0; i < pos && i < input.length(); i++) {
            if (input.charAt(i) == '\n') {
                linha++;
            }
        }
        return linha;
    }

    private String consumirIdentificadorCompleto(String lexemeAtual) throws LexicalError {
    StringBuilder sb = new StringBuilder(lexemeAtual);
    while (position < input.length() && input.charAt(position) == '_') {
        int pos = position + 1;
        if (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            sb.append('_');
            position++;
            while (position < input.length() && Character.isDigit(input.charAt(position))) {
                sb.append(input.charAt(position));
                position++;
            }
        } else {
            int linha = getLine(position);
            throw new LexicalError("linha " + linha + ": identificador inválido");
        }
    }
    return sb.toString();
    }

    public Token nextToken() throws LexicalError
    {
        if (!hasInput())
            return null;

        int start = position;
        int state = 0;
        int lastState = 0;
        int endState = -1;
        int end = -1;

        while (hasInput())
        {
            lastState = state;
            state = nextState(nextChar(), state);

            if (state < 0)
                break;
            else
            {
                if (tokenForState(state) >= 0)
                {
                    endState = state;
                    end = position;
                }
            }
        }

        if (endState < 0 || tokenForState(lastState) == -2)
        {
            int linha = getLine(start);
            String msg = SCANNER_ERROR[lastState];

            if (msg == null || msg.isEmpty() || msg.contains("Caractere") || msg.contains("esperado"))
            {
                msg = input.charAt(start) + " símbolo inválido";
            }

            if (msg.contains("cte_float"))
                msg = "constante_float inválida";
            else if (msg.contains("cte_char"))
                msg = "constante_char inválida";
            else if (msg.contains("cte_string"))
                msg = "constante_string inválida";
            else if (msg.contains("bloco"))
                msg = "comentário inválido ou não finalizado";

            throw new LexicalError("linha " + linha + ": " + msg);
        }

        position = end;

        int token = tokenForState(endState);

        if (token == 0 || token == t_linha || token == t_bloco)
            return nextToken();
        else
        {
            String lexeme = input.substring(start, end);

            if (token == t_cte_int) {
                if (position < input.length() && input.charAt(position) == '.') {
                    int nextPos = position + 1;
                    if (nextPos >= input.length() || !Character.isDigit(input.charAt(nextPos))) {
                        int linha = getLine(start);
                        throw new LexicalError("linha " + linha + ": constante_float inválida");
                    }
                }
            }

            if (token == t_id) {
                lexeme = consumirIdentificadorCompleto(lexeme);
            }

            token = lookupToken(token, lexeme);
            return new Token(token, lexeme, start);
        }
    }

    private int nextState(char c, int state)
    {
        int start = SCANNER_TABLE_INDEXES[state];
        int end   = SCANNER_TABLE_INDEXES[state+1]-1;

        while (start <= end)
        {
            int half = (start+end)/2;

            if (SCANNER_TABLE[half][0] == c)
                return SCANNER_TABLE[half][1];
            else if (SCANNER_TABLE[half][0] < c)
                start = half+1;
            else
                end = half-1;
        }

        return -1;
    }

    private int tokenForState(int state)
    {
        if (state < 0 || state >= TOKEN_STATE.length)
            return -1;

        return TOKEN_STATE[state];
    }

    public int lookupToken(int base, String key)
    {
        int start = SPECIAL_CASES_INDEXES[base];
        int end   = SPECIAL_CASES_INDEXES[base+1]-1;

        while (start <= end)
        {
            int half = (start+end)/2;
            int comp = SPECIAL_CASES_KEYS[half].compareTo(key);

            if (comp == 0)
                return SPECIAL_CASES_VALUES[half];
            else if (comp < 0)
                start = half+1;
            else
                end = half-1;
        }

        return base;
    }

    private boolean hasInput()
    {
        return position < input.length();
    }

    private char nextChar()
    {
        if (hasInput())
            return input.charAt(position++);
        else
            return (char) -1;
    }
}