//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace java.util {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaClassAttribute()]
    public partial class Random : global::java.lang.Object, global::java.io.Serializable {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId _nextDouble0;
        
        internal static global::net.sf.jni4net.jni.MethodId _nextInt1;
        
        internal static global::net.sf.jni4net.jni.MethodId _nextInt2;
        
        internal static global::net.sf.jni4net.jni.MethodId _nextBoolean3;
        
        internal static global::net.sf.jni4net.jni.MethodId _nextBytes4;
        
        internal static global::net.sf.jni4net.jni.MethodId _nextFloat5;
        
        internal static global::net.sf.jni4net.jni.MethodId _nextGaussian6;
        
        internal static global::net.sf.jni4net.jni.MethodId _nextLong7;
        
        internal static global::net.sf.jni4net.jni.MethodId _setSeed8;
        
        internal static global::net.sf.jni4net.jni.MethodId @__ctorRandom9;
        
        internal static global::net.sf.jni4net.jni.MethodId @__ctorRandom10;
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(J)V")]
        public Random(long par0) : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.NewObject(global::java.util.Random.staticClass, global::java.util.Random.@__ctorRandom9, this, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()V")]
        public Random() : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            @__env.NewObject(global::java.util.Random.staticClass, global::java.util.Random.@__ctorRandom10, this);
            }
        }
        
        protected Random(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        public static global::java.lang.Class _class {
            get {
                return global::java.util.Random.staticClass;
            }
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::java.util.Random.staticClass = @__class;
            global::java.util.Random._nextDouble0 = @__env.GetMethodID(global::java.util.Random.staticClass, "nextDouble", "()D");
            global::java.util.Random._nextInt1 = @__env.GetMethodID(global::java.util.Random.staticClass, "nextInt", "(I)I");
            global::java.util.Random._nextInt2 = @__env.GetMethodID(global::java.util.Random.staticClass, "nextInt", "()I");
            global::java.util.Random._nextBoolean3 = @__env.GetMethodID(global::java.util.Random.staticClass, "nextBoolean", "()Z");
            global::java.util.Random._nextBytes4 = @__env.GetMethodID(global::java.util.Random.staticClass, "nextBytes", "([B)V");
            global::java.util.Random._nextFloat5 = @__env.GetMethodID(global::java.util.Random.staticClass, "nextFloat", "()F");
            global::java.util.Random._nextGaussian6 = @__env.GetMethodID(global::java.util.Random.staticClass, "nextGaussian", "()D");
            global::java.util.Random._nextLong7 = @__env.GetMethodID(global::java.util.Random.staticClass, "nextLong", "()J");
            global::java.util.Random._setSeed8 = @__env.GetMethodID(global::java.util.Random.staticClass, "setSeed", "(J)V");
            global::java.util.Random.@__ctorRandom9 = @__env.GetMethodID(global::java.util.Random.staticClass, "<init>", "(J)V");
            global::java.util.Random.@__ctorRandom10 = @__env.GetMethodID(global::java.util.Random.staticClass, "<init>", "()V");
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()D")]
        public virtual double nextDouble() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((double)(@__env.CallDoubleMethod(this, global::java.util.Random._nextDouble0)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(I)I")]
        public virtual int nextInt(int par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            return ((int)(@__env.CallIntMethod(this, global::java.util.Random._nextInt1, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par0))));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()I")]
        public virtual int nextInt() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((int)(@__env.CallIntMethod(this, global::java.util.Random._nextInt2)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool nextBoolean() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::java.util.Random._nextBoolean3)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("([B)V")]
        public virtual void nextBytes(byte[] par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::java.util.Random._nextBytes4, global::net.sf.jni4net.utils.Convertor.ParArrayPrimC2J(@__env, par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()F")]
        public virtual float nextFloat() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((float)(@__env.CallFloatMethod(this, global::java.util.Random._nextFloat5)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()D")]
        public virtual double nextGaussian() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((double)(@__env.CallDoubleMethod(this, global::java.util.Random._nextGaussian6)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()J")]
        public virtual long nextLong() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((long)(@__env.CallLongMethod(this, global::java.util.Random._nextLong7)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(J)V")]
        public virtual void setSeed(long par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallVoidMethod(this, global::java.util.Random._setSeed8, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par0));
            }
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::java.util.Random(@__env);
            }
        }
    }
    #endregion
}
