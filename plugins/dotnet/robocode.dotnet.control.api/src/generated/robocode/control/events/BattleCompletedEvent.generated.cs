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
    public partial class BattleCompletedEvent : global::robocode.control.events.BattleEvent {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getBattleRules0;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getSortedResults1;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getIndexedResults2;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n__ctorBattleCompletedEvent3;
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lrobocode/BattleRules;[Lrobocode/BattleResults;)V")]
        public BattleCompletedEvent(global::java.lang.Object par0, java.lang.Object[] par1) : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 14)){
            @__env.NewObject(global::robocode.control.events.BattleCompletedEvent.staticClass, global::robocode.control.events.BattleCompletedEvent.j4n__ctorBattleCompletedEvent3, this, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0), global::net.sf.jni4net.utils.Convertor.ParArrayStrongCp2J(@__env, par1));
            }
        }
        
        protected BattleCompletedEvent(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        public static global::java.lang.Class _class {
            get {
                return global::robocode.control.events.BattleCompletedEvent.staticClass;
            }
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::robocode.control.events.BattleCompletedEvent.staticClass = @__class;
            global::robocode.control.events.BattleCompletedEvent.j4n_getBattleRules0 = @__env.GetMethodID(global::robocode.control.events.BattleCompletedEvent.staticClass, "getBattleRules", "()Lrobocode/BattleRules;");
            global::robocode.control.events.BattleCompletedEvent.j4n_getSortedResults1 = @__env.GetMethodID(global::robocode.control.events.BattleCompletedEvent.staticClass, "getSortedResults", "()[Lrobocode/BattleResults;");
            global::robocode.control.events.BattleCompletedEvent.j4n_getIndexedResults2 = @__env.GetMethodID(global::robocode.control.events.BattleCompletedEvent.staticClass, "getIndexedResults", "()[Lrobocode/BattleResults;");
            global::robocode.control.events.BattleCompletedEvent.j4n__ctorBattleCompletedEvent3 = @__env.GetMethodID(global::robocode.control.events.BattleCompletedEvent.staticClass, "<init>", "(Lrobocode/BattleRules;[Lrobocode/BattleResults;)V");
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Lrobocode/BattleRules;")]
        public virtual global::java.lang.Object getBattleRules() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::java.lang.Object>(@__env, @__env.CallObjectMethodPtr(this, global::robocode.control.events.BattleCompletedEvent.j4n_getBattleRules0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()[Lrobocode/BattleResults;")]
        public virtual java.lang.Object[] getSortedResults() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return global::net.sf.jni4net.utils.Convertor.ArrayStrongJ2Cp<java.lang.Object[], global::java.lang.Object>(@__env, @__env.CallObjectMethodPtr(this, global::robocode.control.events.BattleCompletedEvent.j4n_getSortedResults1));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()[Lrobocode/BattleResults;")]
        public virtual java.lang.Object[] getIndexedResults() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return global::net.sf.jni4net.utils.Convertor.ArrayStrongJ2Cp<java.lang.Object[], global::java.lang.Object>(@__env, @__env.CallObjectMethodPtr(this, global::robocode.control.events.BattleCompletedEvent.j4n_getIndexedResults2));
            }
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::robocode.control.events.BattleCompletedEvent(@__env);
            }
        }
    }
    #endregion
}
