//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:2.0.50727.5456
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace robocode.control.events {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaClassAttribute()]
    public partial class BattleErrorEvent : global::robocode.control.events.BattleEvent {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getError0;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n__ctorBattleErrorEvent1;
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/String;)V")]
        public BattleErrorEvent(global::java.lang.String par0) : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.NewObject(global::robocode.control.events.BattleErrorEvent.staticClass, global::robocode.control.events.BattleErrorEvent.j4n__ctorBattleErrorEvent1, this, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        protected BattleErrorEvent(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        public static global::java.lang.Class _class {
            get {
                return global::robocode.control.events.BattleErrorEvent.staticClass;
            }
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::robocode.control.events.BattleErrorEvent.staticClass = @__class;
            global::robocode.control.events.BattleErrorEvent.j4n_getError0 = @__env.GetMethodID(global::robocode.control.events.BattleErrorEvent.staticClass, "getError", "()Ljava/lang/String;");
            global::robocode.control.events.BattleErrorEvent.j4n__ctorBattleErrorEvent1 = @__env.GetMethodID(global::robocode.control.events.BattleErrorEvent.staticClass, "<init>", "(Ljava/lang/String;)V");
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Ljava/lang/String;")]
        public virtual global::java.lang.String getError() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return global::net.sf.jni4net.utils.Convertor.StrongJ2CpString(@__env, @__env.CallObjectMethodPtr(this, global::robocode.control.events.BattleErrorEvent.j4n_getError0));
            }
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::robocode.control.events.BattleErrorEvent(@__env);
            }
        }
    }
    #endregion
}
