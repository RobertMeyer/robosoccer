//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:2.0.50727.4927
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace robocode.control.snapshot {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaInterfaceAttribute()]
    public partial interface IBulletSnapshot {
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Lrobocode/control/snapshot/BulletState;")]
        global::robocode.control.snapshot.BulletState getState();
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()D")]
        double getPower();
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()D")]
        double getX();
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()D")]
        double getY();
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()D")]
        double getPaintX();
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()D")]
        double getPaintY();
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()I")]
        int getColor();
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()I")]
        int getFrame();
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        bool isExplosion();
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()I")]
        int getExplosionImageIndex();
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()I")]
        int getBulletId();
    }
    #endregion
    
    #region Component Designer generated code 
    public partial class IBulletSnapshot_ {
        
        public static global::java.lang.Class _class {
            get {
                return global::robocode.control.snapshot.@__IBulletSnapshot.staticClass;
            }
        }
    }
    #endregion
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaProxyAttribute(typeof(global::robocode.control.snapshot.IBulletSnapshot), typeof(global::robocode.control.snapshot.IBulletSnapshot_))]
    [global::net.sf.jni4net.attributes.ClrWrapperAttribute(typeof(global::robocode.control.snapshot.IBulletSnapshot), typeof(global::robocode.control.snapshot.IBulletSnapshot_))]
    internal sealed partial class @__IBulletSnapshot : global::java.lang.Object, global::robocode.control.snapshot.IBulletSnapshot {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId _getState0;
        
        internal static global::net.sf.jni4net.jni.MethodId _getPower1;
        
        internal static global::net.sf.jni4net.jni.MethodId _getX2;
        
        internal static global::net.sf.jni4net.jni.MethodId _getY3;
        
        internal static global::net.sf.jni4net.jni.MethodId _getPaintX4;
        
        internal static global::net.sf.jni4net.jni.MethodId _getPaintY5;
        
        internal static global::net.sf.jni4net.jni.MethodId _getColor6;
        
        internal static global::net.sf.jni4net.jni.MethodId _getFrame7;
        
        internal static global::net.sf.jni4net.jni.MethodId _isExplosion8;
        
        internal static global::net.sf.jni4net.jni.MethodId _getExplosionImageIndex9;
        
        internal static global::net.sf.jni4net.jni.MethodId _getBulletId10;
        
        private @__IBulletSnapshot(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::robocode.control.snapshot.@__IBulletSnapshot.staticClass = @__class;
            global::robocode.control.snapshot.@__IBulletSnapshot._getState0 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "getState", "()Lrobocode/control/snapshot/BulletState;");
            global::robocode.control.snapshot.@__IBulletSnapshot._getPower1 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "getPower", "()D");
            global::robocode.control.snapshot.@__IBulletSnapshot._getX2 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "getX", "()D");
            global::robocode.control.snapshot.@__IBulletSnapshot._getY3 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "getY", "()D");
            global::robocode.control.snapshot.@__IBulletSnapshot._getPaintX4 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "getPaintX", "()D");
            global::robocode.control.snapshot.@__IBulletSnapshot._getPaintY5 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "getPaintY", "()D");
            global::robocode.control.snapshot.@__IBulletSnapshot._getColor6 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "getColor", "()I");
            global::robocode.control.snapshot.@__IBulletSnapshot._getFrame7 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "getFrame", "()I");
            global::robocode.control.snapshot.@__IBulletSnapshot._isExplosion8 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "isExplosion", "()Z");
            global::robocode.control.snapshot.@__IBulletSnapshot._getExplosionImageIndex9 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "getExplosionImageIndex", "()I");
            global::robocode.control.snapshot.@__IBulletSnapshot._getBulletId10 = @__env.GetMethodID(global::robocode.control.snapshot.@__IBulletSnapshot.staticClass, "getBulletId", "()I");
        }
        
        public global::robocode.control.snapshot.BulletState getState() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::robocode.control.snapshot.BulletState>(@__env, @__env.CallObjectMethodPtr(this, global::robocode.control.snapshot.@__IBulletSnapshot._getState0));
        }
        
        public double getPower() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return ((double)(@__env.CallDoubleMethod(this, global::robocode.control.snapshot.@__IBulletSnapshot._getPower1)));
        }
        
        public double getX() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return ((double)(@__env.CallDoubleMethod(this, global::robocode.control.snapshot.@__IBulletSnapshot._getX2)));
        }
        
        public double getY() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return ((double)(@__env.CallDoubleMethod(this, global::robocode.control.snapshot.@__IBulletSnapshot._getY3)));
        }
        
        public double getPaintX() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return ((double)(@__env.CallDoubleMethod(this, global::robocode.control.snapshot.@__IBulletSnapshot._getPaintX4)));
        }
        
        public double getPaintY() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return ((double)(@__env.CallDoubleMethod(this, global::robocode.control.snapshot.@__IBulletSnapshot._getPaintY5)));
        }
        
        public int getColor() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return ((int)(@__env.CallIntMethod(this, global::robocode.control.snapshot.@__IBulletSnapshot._getColor6)));
        }
        
        public int getFrame() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return ((int)(@__env.CallIntMethod(this, global::robocode.control.snapshot.@__IBulletSnapshot._getFrame7)));
        }
        
        public bool isExplosion() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return ((bool)(@__env.CallBooleanMethod(this, global::robocode.control.snapshot.@__IBulletSnapshot._isExplosion8)));
        }
        
        public int getExplosionImageIndex() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return ((int)(@__env.CallIntMethod(this, global::robocode.control.snapshot.@__IBulletSnapshot._getExplosionImageIndex9)));
        }
        
        public int getBulletId() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            return ((int)(@__env.CallIntMethod(this, global::robocode.control.snapshot.@__IBulletSnapshot._getBulletId10)));
        }
        
        private static global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> @__Init(global::net.sf.jni4net.jni.JNIEnv @__env, global::java.lang.Class @__class) {
            global::System.Type @__type = typeof(__IBulletSnapshot);
            global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> methods = new global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod>();
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getState", "getState0", "()Lrobocode/control/snapshot/BulletState;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getPower", "getPower1", "()D"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getX", "getX2", "()D"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getY", "getY3", "()D"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getPaintX", "getPaintX4", "()D"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getPaintY", "getPaintY5", "()D"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getColor", "getColor6", "()I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getFrame", "getFrame7", "()I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "isExplosion", "isExplosion8", "()Z"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getExplosionImageIndex", "getExplosionImageIndex9", "()I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getBulletId", "getBulletId10", "()I"));
            return methods;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle getState0(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()Lrobocode/control/snapshot/BulletState;
            // ()Lrobocode/control/snapshot/BulletState;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.StrongCp2J(@__real.getState());
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static double getPower1(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()D
            // ()D
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            double @__return = default(double);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = ((double)(@__real.getPower()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static double getX2(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()D
            // ()D
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            double @__return = default(double);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = ((double)(@__real.getX()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static double getY3(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()D
            // ()D
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            double @__return = default(double);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = ((double)(@__real.getY()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static double getPaintX4(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()D
            // ()D
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            double @__return = default(double);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = ((double)(@__real.getPaintX()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static double getPaintY5(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()D
            // ()D
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            double @__return = default(double);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = ((double)(@__real.getPaintY()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int getColor6(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()I
            // ()I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = ((int)(@__real.getColor()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int getFrame7(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()I
            // ()I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = ((int)(@__real.getFrame()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static bool isExplosion8(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()Z
            // ()Z
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            bool @__return = default(bool);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = ((bool)(@__real.isExplosion()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int getExplosionImageIndex9(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()I
            // ()I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = ((int)(@__real.getExplosionImageIndex()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int getBulletId10(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()I
            // ()I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            global::robocode.control.snapshot.IBulletSnapshot @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::robocode.control.snapshot.IBulletSnapshot>(@__env, @__obj);
            @__return = ((int)(@__real.getBulletId()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::robocode.control.snapshot.@__IBulletSnapshot(@__env);
            }
        }
    }
    #endregion
}
