//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:2.0.50727.5456
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace net.sf.robocode.repository {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaClassAttribute()]
    public partial class RobotType : global::java.lang.Object, global::java.io.Serializable {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_isValid0;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_isDroid1;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_isTeamRobot2;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_isAdvancedRobot3;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_isStandardRobot4;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_isInteractiveRobot5;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_isPaintRobot6;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_isJuniorRobot7;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getCode8;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_INVALID9;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_JUNIOR10;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_STANDARD11;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_ADVANCED12;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_TEAM13;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_DROID14;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_INTERACTIVE15;
        
        internal static global::net.sf.jni4net.jni.FieldId j4n_PAINTING16;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n__ctorRobotType17;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n__ctorRobotType18;
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(ZZZZZZZ)V")]
        public RobotType(bool par0, bool par1, bool par2, bool par3, bool par4, bool par5, bool par6) : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 24)){
            @__env.NewObject(global::net.sf.robocode.repository.RobotType.staticClass, global::net.sf.robocode.repository.RobotType.j4n__ctorRobotType17, this, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par0), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par1), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par2), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par3), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par4), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par5), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par6));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(I)V")]
        public RobotType(int par0) : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.NewObject(global::net.sf.robocode.repository.RobotType.staticClass, global::net.sf.robocode.repository.RobotType.j4n__ctorRobotType18, this, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par0));
            }
        }
        
        protected RobotType(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        public static global::java.lang.Class _class {
            get {
                return global::net.sf.robocode.repository.RobotType.staticClass;
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/repository/RobotType;")]
        public static global::net.sf.robocode.repository.RobotType INVALID {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.repository.RobotType>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.repository.RobotType.staticClass, global::net.sf.robocode.repository.RobotType.j4n_INVALID9));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/repository/RobotType;")]
        public static global::net.sf.robocode.repository.RobotType JUNIOR {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.repository.RobotType>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.repository.RobotType.staticClass, global::net.sf.robocode.repository.RobotType.j4n_JUNIOR10));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/repository/RobotType;")]
        public static global::net.sf.robocode.repository.RobotType STANDARD {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.repository.RobotType>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.repository.RobotType.staticClass, global::net.sf.robocode.repository.RobotType.j4n_STANDARD11));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/repository/RobotType;")]
        public static global::net.sf.robocode.repository.RobotType ADVANCED {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.repository.RobotType>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.repository.RobotType.staticClass, global::net.sf.robocode.repository.RobotType.j4n_ADVANCED12));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/repository/RobotType;")]
        public static global::net.sf.robocode.repository.RobotType TEAM {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.repository.RobotType>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.repository.RobotType.staticClass, global::net.sf.robocode.repository.RobotType.j4n_TEAM13));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/repository/RobotType;")]
        public static global::net.sf.robocode.repository.RobotType DROID {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.repository.RobotType>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.repository.RobotType.staticClass, global::net.sf.robocode.repository.RobotType.j4n_DROID14));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/repository/RobotType;")]
        public static global::net.sf.robocode.repository.RobotType INTERACTIVE {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.repository.RobotType>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.repository.RobotType.staticClass, global::net.sf.robocode.repository.RobotType.j4n_INTERACTIVE15));
            }
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("Lnet/sf/robocode/repository/RobotType;")]
        public static global::net.sf.robocode.repository.RobotType PAINTING {
            get {
                global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
                return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::net.sf.robocode.repository.RobotType>(@__env, @__env.GetStaticObjectFieldPtr(global::net.sf.robocode.repository.RobotType.staticClass, global::net.sf.robocode.repository.RobotType.j4n_PAINTING16));
            }
            }
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::net.sf.robocode.repository.RobotType.staticClass = @__class;
            global::net.sf.robocode.repository.RobotType.j4n_isValid0 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "isValid", "()Z");
            global::net.sf.robocode.repository.RobotType.j4n_isDroid1 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "isDroid", "()Z");
            global::net.sf.robocode.repository.RobotType.j4n_isTeamRobot2 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "isTeamRobot", "()Z");
            global::net.sf.robocode.repository.RobotType.j4n_isAdvancedRobot3 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "isAdvancedRobot", "()Z");
            global::net.sf.robocode.repository.RobotType.j4n_isStandardRobot4 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "isStandardRobot", "()Z");
            global::net.sf.robocode.repository.RobotType.j4n_isInteractiveRobot5 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "isInteractiveRobot", "()Z");
            global::net.sf.robocode.repository.RobotType.j4n_isPaintRobot6 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "isPaintRobot", "()Z");
            global::net.sf.robocode.repository.RobotType.j4n_isJuniorRobot7 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "isJuniorRobot", "()Z");
            global::net.sf.robocode.repository.RobotType.j4n_getCode8 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "getCode", "()I");
            global::net.sf.robocode.repository.RobotType.j4n_INVALID9 = @__env.GetStaticFieldID(global::net.sf.robocode.repository.RobotType.staticClass, "INVALID", "Lnet/sf/robocode/repository/RobotType;");
            global::net.sf.robocode.repository.RobotType.j4n_JUNIOR10 = @__env.GetStaticFieldID(global::net.sf.robocode.repository.RobotType.staticClass, "JUNIOR", "Lnet/sf/robocode/repository/RobotType;");
            global::net.sf.robocode.repository.RobotType.j4n_STANDARD11 = @__env.GetStaticFieldID(global::net.sf.robocode.repository.RobotType.staticClass, "STANDARD", "Lnet/sf/robocode/repository/RobotType;");
            global::net.sf.robocode.repository.RobotType.j4n_ADVANCED12 = @__env.GetStaticFieldID(global::net.sf.robocode.repository.RobotType.staticClass, "ADVANCED", "Lnet/sf/robocode/repository/RobotType;");
            global::net.sf.robocode.repository.RobotType.j4n_TEAM13 = @__env.GetStaticFieldID(global::net.sf.robocode.repository.RobotType.staticClass, "TEAM", "Lnet/sf/robocode/repository/RobotType;");
            global::net.sf.robocode.repository.RobotType.j4n_DROID14 = @__env.GetStaticFieldID(global::net.sf.robocode.repository.RobotType.staticClass, "DROID", "Lnet/sf/robocode/repository/RobotType;");
            global::net.sf.robocode.repository.RobotType.j4n_INTERACTIVE15 = @__env.GetStaticFieldID(global::net.sf.robocode.repository.RobotType.staticClass, "INTERACTIVE", "Lnet/sf/robocode/repository/RobotType;");
            global::net.sf.robocode.repository.RobotType.j4n_PAINTING16 = @__env.GetStaticFieldID(global::net.sf.robocode.repository.RobotType.staticClass, "PAINTING", "Lnet/sf/robocode/repository/RobotType;");
            global::net.sf.robocode.repository.RobotType.j4n__ctorRobotType17 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "<init>", "(ZZZZZZZ)V");
            global::net.sf.robocode.repository.RobotType.j4n__ctorRobotType18 = @__env.GetMethodID(global::net.sf.robocode.repository.RobotType.staticClass, "<init>", "(I)V");
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool isValid() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::net.sf.robocode.repository.RobotType.j4n_isValid0)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool isDroid() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::net.sf.robocode.repository.RobotType.j4n_isDroid1)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool isTeamRobot() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::net.sf.robocode.repository.RobotType.j4n_isTeamRobot2)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool isAdvancedRobot() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::net.sf.robocode.repository.RobotType.j4n_isAdvancedRobot3)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool isStandardRobot() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::net.sf.robocode.repository.RobotType.j4n_isStandardRobot4)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool isInteractiveRobot() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::net.sf.robocode.repository.RobotType.j4n_isInteractiveRobot5)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool isPaintRobot() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::net.sf.robocode.repository.RobotType.j4n_isPaintRobot6)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool isJuniorRobot() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::net.sf.robocode.repository.RobotType.j4n_isJuniorRobot7)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()I")]
        public virtual int getCode() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((int)(@__env.CallIntMethod(this, global::net.sf.robocode.repository.RobotType.j4n_getCode8)));
            }
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::net.sf.robocode.repository.RobotType(@__env);
            }
        }
    }
    #endregion
}
