//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace net.sf.robocode.io {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaClassAttribute()]
    public partial class Logger : global::java.lang.Object {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId _logError0;
        
        internal static global::net.sf.jni4net.jni.MethodId _logError1;
        
        internal static global::net.sf.jni4net.jni.MethodId _logError2;
        
        internal static global::net.sf.jni4net.jni.MethodId _setLogListener3;
        
        internal static global::net.sf.jni4net.jni.MethodId _logMessage4;
        
        internal static global::net.sf.jni4net.jni.MethodId _logMessage5;
        
        internal static global::net.sf.jni4net.jni.MethodId _logWarning6;
        
        internal static global::net.sf.jni4net.jni.MethodId _printlnToRobotsConsole7;
        
        internal static global::net.sf.jni4net.jni.FieldId _realOut8;
        
        internal static global::net.sf.jni4net.jni.FieldId _realErr9;
        
        internal static global::net.sf.jni4net.jni.MethodId @__ctorLogger10;
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()V")]
        public Logger() : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            @__env.NewObject(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger.@__ctorLogger10, this);
            }
        }
        
        protected Logger(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        public static global::java.lang.Class _class {
            get {
                return global::net.sf.robocode.io.Logger.staticClass;
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Ljava/io/PrintStream;")]
        public static global::java.io.PrintStream realOut {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::java.io.PrintStream>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger._realOut8));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Ljava/io/PrintStream;")]
        public static global::java.io.PrintStream realErr {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::java.io.PrintStream>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger._realErr9));
            }
            }
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::net.sf.robocode.io.Logger.staticClass = @__class;
            global::net.sf.robocode.io.Logger._logError0 = @__env.GetStaticMethodID(global::net.sf.robocode.io.Logger.staticClass, "logError", "(Ljava/lang/Throwable;)V");
            global::net.sf.robocode.io.Logger._logError1 = @__env.GetStaticMethodID(global::net.sf.robocode.io.Logger.staticClass, "logError", "(Ljava/lang/String;Ljava/lang/Throwable;)V");
            global::net.sf.robocode.io.Logger._logError2 = @__env.GetStaticMethodID(global::net.sf.robocode.io.Logger.staticClass, "logError", "(Ljava/lang/String;)V");
            global::net.sf.robocode.io.Logger._setLogListener3 = @__env.GetStaticMethodID(global::net.sf.robocode.io.Logger.staticClass, "setLogListener", "(Lrobocode/control/events/IBattleListener;)V");
            global::net.sf.robocode.io.Logger._logMessage4 = @__env.GetStaticMethodID(global::net.sf.robocode.io.Logger.staticClass, "logMessage", "(Ljava/lang/String;)V");
            global::net.sf.robocode.io.Logger._logMessage5 = @__env.GetStaticMethodID(global::net.sf.robocode.io.Logger.staticClass, "logMessage", "(Ljava/lang/String;Z)V");
            global::net.sf.robocode.io.Logger._logWarning6 = @__env.GetStaticMethodID(global::net.sf.robocode.io.Logger.staticClass, "logWarning", "(Ljava/lang/String;)V");
            global::net.sf.robocode.io.Logger._printlnToRobotsConsole7 = @__env.GetStaticMethodID(global::net.sf.robocode.io.Logger.staticClass, "printlnToRobotsConsole", "(Ljava/lang/String;)V");
            global::net.sf.robocode.io.Logger._realOut8 = @__env.GetStaticFieldID(global::net.sf.robocode.io.Logger.staticClass, "realOut", "Ljava/io/PrintStream;");
            global::net.sf.robocode.io.Logger._realErr9 = @__env.GetStaticFieldID(global::net.sf.robocode.io.Logger.staticClass, "realErr", "Ljava/io/PrintStream;");
            global::net.sf.robocode.io.Logger.@__ctorLogger10 = @__env.GetMethodID(global::net.sf.robocode.io.Logger.staticClass, "<init>", "()V");
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/Throwable;)V")]
        public static void logError(global::java.lang.Throwable par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallStaticVoidMethod(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger._logError0, global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::java.lang.Throwable>(@__env, par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/String;Ljava/lang/Throwable;)V")]
        public static void logError(global::java.lang.String par0, global::java.lang.Throwable par1) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 14)){
            @__env.CallStaticVoidMethod(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger._logError1, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0), global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::java.lang.Throwable>(@__env, par1));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/String;)V")]
        public static void logError(global::java.lang.String par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallStaticVoidMethod(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger._logError2, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lrobocode/control/events/IBattleListener;)V")]
        public static void setLogListener(global::java.lang.Object par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallStaticVoidMethod(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger._setLogListener3, global::net.sf.jni4net.utils.Convertor.ParFullC2J<global::java.lang.Object>(@__env, par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/String;)V")]
        public static void logMessage(global::java.lang.String par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallStaticVoidMethod(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger._logMessage4, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/String;Z)V")]
        public static void logMessage(global::java.lang.String par0, bool par1) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 14)){
            @__env.CallStaticVoidMethod(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger._logMessage5, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par1));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/String;)V")]
        public static void logWarning(global::java.lang.String par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallStaticVoidMethod(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger._logWarning6, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/String;)V")]
        public static void printlnToRobotsConsole(global::java.lang.String par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallStaticVoidMethod(global::net.sf.robocode.io.Logger.staticClass, global::net.sf.robocode.io.Logger._printlnToRobotsConsole7, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::net.sf.robocode.io.Logger(@__env);
            }
        }
    }
    #endregion
}
