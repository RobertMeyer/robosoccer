//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:2.0.50727.5456
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace net.sf.robocode.core {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaClassAttribute()]
    public partial class ContainerBase : global::java.lang.Object {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getComponent0;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_instance1;
        
        protected ContainerBase(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        public static global::java.lang.Class _class {
            get {
                return global::net.sf.robocode.core.ContainerBase.staticClass;
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/core/ContainerBase;")]
        public static global::net.sf.robocode.core.ContainerBase instance {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.core.ContainerBase>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.core.ContainerBase.staticClass, global::net.sf.robocode.core.ContainerBase.j4n_instance1));
            }
            }
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::net.sf.robocode.core.ContainerBase.staticClass = @__class;
            global::net.sf.robocode.core.ContainerBase.j4n_getComponent0 = @__env.GetStaticMethodID(global::net.sf.robocode.core.ContainerBase.staticClass, "getComponent", "(Ljava/lang/Class;)Ljava/lang/Object;");
            global::net.sf.robocode.core.ContainerBase.j4n_instance1 = @__env.GetStaticFieldID(global::net.sf.robocode.core.ContainerBase.staticClass, "instance", "Lnet/sf/robocode/core/ContainerBase;");
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/lang/Class;)Ljava/lang/Object;")]
        public static global::java.lang.Object getComponent(global::java.lang.Class par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            return global::net.sf.jni4net.utils.Convertor.FullJ2C<global::java.lang.Object>(@__env, @__env.CallStaticObjectMethodPtr(global::net.sf.robocode.core.ContainerBase.staticClass, global::net.sf.robocode.core.ContainerBase.j4n_getComponent0, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0)));
            }
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::net.sf.robocode.core.ContainerBase(@__env);
            }
        }
    }
    #endregion
}
