//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:2.0.50727.5456
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace net.sf.robocode.peer {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaClassAttribute()]
    public partial class BadBehavior : global::java.lang.Object {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_valueOf0;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_CANNOT_START1;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_SKIPPED_TOO_MANY_TURNS2;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_UNSTOPPABLE3;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_SECURITY_VIOLATION4;
        
        protected BadBehavior(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        public static global::java.lang.Class _class {
            get {
                return global::net.sf.robocode.peer.BadBehavior.staticClass;
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/peer/BadBehavior;")]
        public static global::net.sf.robocode.peer.BadBehavior CANNOT_START {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.peer.BadBehavior>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.peer.BadBehavior.staticClass, global::net.sf.robocode.peer.BadBehavior.j4n_CANNOT_START1));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/peer/BadBehavior;")]
        public static global::net.sf.robocode.peer.BadBehavior SKIPPED_TOO_MANY_TURNS {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.peer.BadBehavior>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.peer.BadBehavior.staticClass, global::net.sf.robocode.peer.BadBehavior.j4n_SKIPPED_TOO_MANY_TURNS2));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/peer/BadBehavior;")]
        public static global::net.sf.robocode.peer.BadBehavior UNSTOPPABLE {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.peer.BadBehavior>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.peer.BadBehavior.staticClass, global::net.sf.robocode.peer.BadBehavior.j4n_UNSTOPPABLE3));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/peer/BadBehavior;")]
        public static global::net.sf.robocode.peer.BadBehavior SECURITY_VIOLATION {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.peer.BadBehavior>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.peer.BadBehavior.staticClass, global::net.sf.robocode.peer.BadBehavior.j4n_SECURITY_VIOLATION4));
            }
            }
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::net.sf.robocode.peer.BadBehavior.staticClass = @__class;
            global::net.sf.robocode.peer.BadBehavior.j4n_valueOf0 = @__env.GetStaticMethodID(global::net.sf.robocode.peer.BadBehavior.staticClass, "valueOf", "(Ljava/lang/String;)Lnet/sf/robocode/peer/BadBehavior;");
            global::net.sf.robocode.peer.BadBehavior.j4n_CANNOT_START1 = @__env.GetStaticFieldID(global::net.sf.robocode.peer.BadBehavior.staticClass, "CANNOT_START", "Lnet/sf/robocode/peer/BadBehavior;");
            global::net.sf.robocode.peer.BadBehavior.j4n_SKIPPED_TOO_MANY_TURNS2 = @__env.GetStaticFieldID(global::net.sf.robocode.peer.BadBehavior.staticClass, "SKIPPED_TOO_MANY_TURNS", "Lnet/sf/robocode/peer/BadBehavior;");
            global::net.sf.robocode.peer.BadBehavior.j4n_UNSTOPPABLE3 = @__env.GetStaticFieldID(global::net.sf.robocode.peer.BadBehavior.staticClass, "UNSTOPPABLE", "Lnet/sf/robocode/peer/BadBehavior;");
            global::net.sf.robocode.peer.BadBehavior.j4n_SECURITY_VIOLATION4 = @__env.GetStaticFieldID(global::net.sf.robocode.peer.BadBehavior.staticClass, "SECURITY_VIOLATION", "Lnet/sf/robocode/peer/BadBehavior;");
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/String;)Lnet/sf/robocode/peer/BadBehavior;")]
        public static global::net.sf.robocode.peer.BadBehavior valueOf(global::java.lang.String par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.peer.BadBehavior>(@__env, @__env.CallStaticObjectMethodPtr(global::net.sf.robocode.peer.BadBehavior.staticClass, global::net.sf.robocode.peer.BadBehavior.j4n_valueOf0, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0)));
            }
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::net.sf.robocode.peer.BadBehavior(@__env);
            }
        }
    }
    #endregion
}
