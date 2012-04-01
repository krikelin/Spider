package com.krikelin.spider;

import java.io.Console;
import java.util.Hashtable;

import sun.misc.Regexp;
import sun.org.mozilla.javascript.internal.Delegator;

/// <summary>
/// This is an simple MakoEngine implementation for Jint.
/// Used to minic the Spotify's view engine.
/// 
/// </summary>
public class MakoEngine
{
    /// <summary>
    /// Invokes an method on the script
    /// </summary>
    /// <param name="method">method name</param>
    /// <param name="args">arguments passed to the function</param>
    /// <returns></returns>
    public Object invoke(String method, Object[] args)
    {
        return this.runtimeMachine.invoke(method, args);
    }
    /// <summary>
    /// Returns the old output
    /// </summary>
    public String OldOutput;
    /// <summary>
    /// Raises the create event handler. Useful to add features to the engine before running
    /// </summary>
    /// <param name="sender">The current instance of MakoEngine</param>
    /// <param name="e">EventArg</param>

    public CreateEventHandler create;
    public MakoEngine()
    {
        // Initialize runtime engine. For now we use JavaScriptEngine
        runtimeMachine = new LuaEngine(this);

        // Set JSPython to try as default
       // JSPython = true;
        
        // Raise create event handler 
        if (this.create != null)
        {
            this.create.invoke(this, new EventArgs());
        }
    }
    public String output = "";
   
    /// <summary>
    /// Synchronize data is called by the javascript preparser to get an ready to use JSON parsed data. If the dat can't be parsed as JSON
    /// it will be returned as an common String
    /// </summary>
    /// <param name="uri">The address to the remote information to retrieve</param>
    /// <returns></returns>
   

    /// <summary>
    /// Function to convert variable signatures to variable concations for the parser
    /// </summary>
    /// <param name="Line"></param>
    /// <param name="signature"></param>
    /// <returns></returns>
    public String handleToTokens(String Line, char signature)
    {
        
        Hashtable<String, Object> Variables = new Hashtable<String, Object>();
        // The index of the beginning of an varialbe statement @{
        int IndexOf = 0;
        /**
         * Iterate through all indexes of the @{ statemenet until it ends (IndexOf will return -1  becase IndexOf will gain
         * the new IndexOf with the new statement
         * */
        if(Line.length() > 0)
        while (IndexOf != -1)
        {
            IndexOf = Line.indexOf(signature + "{", IndexOf);
            if (IndexOf == -1)
                break;
            // Gain the index of the next occuranse of the @{ varialbe
            
            int endToken = Line.indexOf('}', IndexOf);

            int startIndex = IndexOf + 2;

            // Get the data inside the token
            String Parseable = Line.substring(startIndex,  endToken );

            // Convert the inline token to concation
            Line = Line.replace("${" + Parseable + "}",  "\" ..  "  + Parseable + "  .. \"");
            IndexOf = endToken;
           
        }
        return Line;
     

    }
    /// <summary>
    /// This function returns variable from the parser embedded in an output field, asserted with an custom sign {VARNAME}
    /// </summary>
    /// <param name="Line">The code line to execute</param>
    /// <param name="signature">The char signature</param>
    /// <returns>An list of processed variables</returns>
    public Hashtable<String, Object> getVariables(String Line, char signature)
    {
        Hashtable<String,Object> Variables = new Hashtable<String,Object>();
        // The index of the beginning of an varialbe statement @{
        int IndexOf = 0;
        /**
         * Iterate through all indexes of the @{ statemenet until it ends (IndexOf will return -1  becase IndexOf will gain
         * the new IndexOf with the new statement
         * */
        while (IndexOf != -1)
        {
            // Gain the index of the next occuranse of the @{ varialbe
            IndexOf  = Line.indexOf(signature+"{");
            int endToken = Line.indexOf("}", IndexOf);

            int startIndex = IndexOf+2;

            // Get the data inside the token
            String Parseable = Line.substring(startIndex, startIndex + endToken - 1);

            // Convert it into an variable
            String[] Result = executeScalarVariable(Parseable, ":", "|", true);
            Variables.put(Result[0], Result[1]);
        }
        return Variables;
       
    }
    /// <summary>
    /// This function executes the scalar variable, works together with GetVariable. It will also parse the inherited 
    /// codebase.
    /// </summary>
    /// <param name="Variable"></param>
    /// <param name="reflector">Reflector divides which is the conditinoal statement and the booleanean output</param>
    /// <param name="divider">booleanean divider</param>
    /// <param name="vetero">Which variable beside the reflector divider should be present in fallback</param>
    /// <value>Returns an 2 field String array where {InitialVariableName,Output}</value>
    /// <returns></returns>
    public String[] executeScalarVariable(String Variable,String reflector, String divider,boolean vetero)
    {
        // An variable in this instruction {booleanVar} : return1 | return 2
        if (Variable.contains(reflector) && Variable.contains(divider))
        {
            // Get the codebase
            String Codebase = Variable.split(reflector)[0];

            // Run the codebase
            Object d = runtimeMachine.run(Codebase);

            // If it are an booleanean decide it, otherwise return the left/right variable as fallback decided by the vetero varialbe
            if (d instanceof Boolean)
            {
                // Get the two case output
                String[] c = Variable.split(reflector)[1].split(divider);

                // Return the decition
                String output =  (Boolean)d ? c[0] : c[1];
                return new String[] { Codebase, output };
            }
            else
            {
                String[] c = Variable.split(reflector)[1].split(divider);

                // Return the case fallback
                String output = vetero ? c[0] : c[1];
                return new String[] { Codebase, output };
            }
        }
       
        /**
            * Otherwise return the value of the variable asserted by the current state of the execution instance
            * */
      
        // Output data
        Object _output = runtimeMachine.run("return " + Variable + ";");
        if (_output instanceof String)
        {
            return new String[]{Variable,(String)_output};
        }

        return new String[]{Variable,Variable};
       
        
    }
    /// <summary>
    /// The javascript will be like as python
    /// </summary>
    public boolean JSPython;
    /// <summary>
    /// Instance of the Jint engine running at runtime
    /// </summary>
    public IScriptEngine runtimeMachine;
    /// <summary>
    /// This function executes String in the js mako engine
    /// </summary>
    /// <param name="e"></param>
    public void Execute(String e)
    {
       
        runtimeMachine.run(e);
    }
   
    /// <summary>
    /// Occurs on request overlay
    /// </summary>
    public OverlayEventHandler requestOverlay;

    /// <summary>
    /// Occurs when overlay has been finished request
    /// </summary>
    public OverlayEventHandler overlayApplied;
	private boolean onlyPreprocess;
    /// <summary>
    /// This function preprosses the mako layer
    /// </summary>
    /// <param name="input">The input String to parse</param>
    /// <param name="argument">The argument sent to the parser</param>
    public String preprocess(String input, String argument, boolean inflate, String uri, boolean onlyPreprocess)
    {
   //     #region OverlayManager Experimental
        /****
         * STOCKHOLM 2011-07-01 14:45
         * 
         * New feature: Apply custom overlays specified by individual service:
         * Overlay are marked with <#namespace#> where "namespace" is an special
         * kind of <namespace>.xml view file inside an <extension_dir>/views/ folder
         * */
        
        // First gain attention by the event

        OverlayEventArgs args = new OverlayEventArgs(); // Create event args
        args.URI = uri;
        if (requestOverlay != null) {
            requestOverlay.invoke(this, args);
        }
        // if the args has retained the phase, eg. not cancelled
        if (!args.cancel)
        {
            // Substitute the overlay placeholders with the views
            if(args.getViewFolders() != null)
           // for (overlay : args.getViewFolders())
            {
                
          //          String content = SR.ReadToEnd();
                    // Substitute overlay placeholders
            //        input = input.replace("<#" + overlay.Key.replace(".xml","") + "#>", content);
              //      SR.Close();
             
            }
        }

        // Remove unwanted trailings
        Regexp a = new Regexp("<\\#[^\\#]*\\#>");
    //    input = a.replace(input, "");
      

        /**
         * Begin normal operation
         * */


        // Clear the output buffer
        output = "";
        /**
         * Tell the runtime machine about the argument
         * */
        try
        {
            String[] arguments = argument.split(":");
            runtimeMachine.setVariable("parameter", argument.replace(arguments[0] + ":", "").replace(arguments[1] + ":", ""));
            runtimeMachine.setVariable("service", arguments[0]);
        }
        catch (Exception e) { }
        /**
         * This String defines the call-stack of the query
         * This is done before any other preprocessing
         * */
        String CallStack = "";
        String[] lines = input.split("\n");
        /**
         * booleanean indicating which parse mode the parser is in,
         * true = in executable text
         * false = in output line 
         * */
        boolean parseMode = false;
        // booleanean indicating first line is parsing
        boolean firstLine = true;
        /***
         * Iterate through all lines and preprocess the page.
         * If page starts with an % it will be treated as an preparser code or all content
         * inside enclosing <? ?>
         * Two String builders, the first is for the current output segment and the second for the current
         * executable segmetn
         * */
        StringBuilder outputCode =  new StringBuilder();
        StringBuilder executableSegment = new StringBuilder();

        // The buffer for the final preprocessing output
        StringBuilder finalOutput = new StringBuilder();
        // Append initial case
        outputCode.append("");

        // booleanean startCase. true = <? false \n%
        boolean startCase = false;

        // booleanean which tells if the cursor is in the preparse or output part of the buffer (inside or outside an executable segment)
        boolean codeMode = false;
        for(int i=0; i < input.length() ;i++)
        {
             // Check if at an overgoing to an code block
            if(codeMode)
            {
                if((startCase && input.charAt(i) == '?' && input.charAt(i+1) == '>') ||( input.charAt(i) == '\n' && !startCase))
                {
                    codeMode=false;

                    // Jump forward two times if endcase is ?>
                    if(startCase)
                        i++;

                    // Get the final output
                    String codeOutput = executableSegment.toString();
                    // If in JSPython mode, convert all row breaks to ; and other syntax elements
                    if (false)
                    {
                        codeOutput = codeOutput.replace("\n", ";");
                        
                        /**
                         * Convert statements
                         * */
                        codeOutput = codeOutput.replace(":", "{");
                        codeOutput = codeOutput.replace("end", "}");
                       
                        codeOutput = codeOutput.replace("\nif ", "\nif(");
                        codeOutput = codeOutput.replace("then:", "){");
                        codeOutput = codeOutput.replace("do:", "){");

                        codeOutput = codeOutput.replace("endif", "}");
                        


                    }
                    codeOutput = codeOutput.replace("lt;", "<");
                   

                    codeOutput = codeOutput.replace("lower than", "<");
                    codeOutput = codeOutput.replace("lower", "<");

                    codeOutput = codeOutput.replace("higher", ">");

                    codeOutput = codeOutput.replace("highter than", ">");
                    codeOutput = codeOutput.replace("gt;", ">");
                    // Append the code data to the String buffer
                    finalOutput.append(" "+ codeOutput  + " ");
                    
                    

                    // Clear outputcode buffer
                    executableSegment = new StringBuilder();

                    continue;
                }
                executableSegment.append(input.charAt(i));
            }
            else
            {
                // If at end, summarize the String
                if(i == input.length() - 1)
                {
                    // Append the last String
                    outputCode.append(input.charAt(i));
                    // Format output code (Replace " to ¤ and swap back on preprocessing)
                    String OutputCode = outputCode.toString().replace("\"", "¤").replace("\n", "%BR%\");\n" + getPrintMethod() + "(\"");
                    OutputCode = this.handleToTokens(OutputCode.toString(),'$');
                    finalOutput.append("" + getPrintMethod() + "(\"" + OutputCode + "\");");
                   
                }
                try
                {
                    if (((input.charAt(i) == '\n' && input.charAt(i+1)== '%')) || (input.charAt(i)== '<' && input.charAt(i+1)== '?'))
                    {
                        startCase = (input.charAt(i) == '<' &&input.charAt(i+1)== '?');
                        codeMode = true;

                        // Convert tokens to interpretable handles
                        String OutputCode = outputCode.toString().replace("\"", "¤").replace("\n", "%BR%\");\n" + getPrintMethod() + "(\"");
                        OutputCode = this.handleToTokens(OutputCode.toString(), '$');
                        finalOutput.append("" + getPrintMethod() + "(\"" + OutputCode + "\");");

                        // Clear the output code buffer
                        outputCode = new StringBuilder();

                        // Skip two tokens forward to not include those tokens to the code buffer
                        i += 1;
                        continue;
                    }

                }
                catch (Exception e)
                {
                    continue;
                }
                outputCode.append(input.charAt(i));
                
            }
            

        }            
        // if exiting in text mode, append an end of the scalar String
        if (!parseMode)
        {
            CallStack += "\");";
        }
        // Run the code
        runtimeMachine.setFunction("" + getPrintMethod() + "",new Delegate() {

			@Override
			public boolean invoke(Object... args) {
				// TODO Auto-generated method stub
				String values = (String)args[0];
				 MakoEngine.this.output += values.replace("%BR%","\n").replace("¤","\"");
				 return true;
			}
        	
        });
        runtimeMachine.setFunction("synchronize_data",  new Delegate() {
        	@Override
			public boolean invoke(Object... args) {
        		// Create web request
        		return false;
			}
        });
        CallStack = finalOutput.toString();
       
        CallStack = CallStack.replace("\r", "");
        if(!this.onlyPreprocess) {
            /***
             * Try run the page. If there was error return ERROR: <error> message so the
             * handler can choose to present it to the user
             * */
            try
            {
            	this.output = runtimeMachine.run(CallStack);

                /**
                 * Check if the result of the preprocessing is the same as before. If nothing
                 * has changed return NONCHANGE. This is only for rendering whole pages, not inflate.
                 * */
                if (!inflate)
                {
                    if (output == OldOutput)
                        return "NONCHANGE";

                    OldOutput = output;
                }
            }
            catch (Exception e)
            {
               /* // clear output
                this.Output = "";
                // Load error page
                using (System.IO.StreamReader SR = new System.IO.StreamReader("views\\error.xml"))
                {
                    String errorView = new MakoEngine().Preprocess(SR.ReadToEnd(), "", false, "", true);
                    runtimeMachine = new JavaScriptEngine();
                    runtimeMachine.SetFunction("" + getPrintMethod() + "", new Func<String, Object>(" + getPrintMethod() + "));
                    runtimeMachine.SetVariable("error", e.toString() + "\n " );

                    runtimeMachine.run((errorView));
                }*/
            }
            return this.output;
        }
           
        else
        {
            return CallStack;
        }

    }
	private String getPrintMethod() {
		
		
		// TODO Auto-generated method stub
		return "__printx";
	}
}