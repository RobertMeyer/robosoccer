//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:2.0.50727.5456
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace net.sf.robocode.dotnet.host {
    
    
    #region Component Designer generated code 
    public partial class DotnetHost_ {
        
        public static global::java.lang.Class _class {
            get {
                return global::net.sf.robocode.dotnet.host.@__DotnetHost.staticClass;
            }
        }
    }
    #endregion
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaProxyAttribute(typeof(global::net.sf.robocode.dotnet.host.DotnetHost), typeof(global::net.sf.robocode.dotnet.host.DotnetHost_))]
    [global::net.sf.jni4net.attributes.ClrWrapperAttribute(typeof(global::net.sf.robocode.dotnet.host.DotnetHost), typeof(global::net.sf.robocode.dotnet.host.DotnetHost_))]
    internal sealed partial class @__DotnetHost : global::java.lang.Object {
        
        internal new static global::java.lang.Class staticClass;
        
        private @__DotnetHost(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::net.sf.robocode.dotnet.host.@__DotnetHost.staticClass = @__class;
        }
        
        private static global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> @__Init(global::net.sf.jni4net.jni.JNIEnv @__env, global::java.lang.Class @__class) {
            global::System.Type @__type = typeof(__DotnetHost);
            global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> methods = new global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod>();
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "createRobotProxy", "createRobotProxy0", "(Lnet/sf/robocode/host/IHostManager;Lrobocode/control/RobotSpecification;Lnet/sf/" +
                        "robocode/peer/IRobotStatics;Lnet/sf/robocode/peer/IRobotPeer;)Lnet/sf/robocode/h" +
                        "ost/proxies/IHostingRobotProxy;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getReferencedClasses", "getReferencedClasses1", "(Lnet/sf/robocode/repository/IRobotRepositoryItem;)[Ljava/lang/String;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "getRobotType", "getRobotType2", "(Lnet/sf/robocode/repository/IRobotRepositoryItem;ZZ)Lnet/sf/robocode/repository/" +
                        "RobotType;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "createRobotProxy", "createRobotProxy3", "(Lnet/sf/robocode/host/IHostManager;Ljava/lang/Object;Lnet/sf/robocode/peer/IRobo" +
                        "tStatics;Lnet/sf/robocode/peer/IRobotPeer;)Lnet/sf/robocode/host/proxies/IHostin" +
                        "gRobotProxy;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "__ctorDotnetHost0", "__ctorDotnetHost0", "(Lnet/sf/jni4net/inj/IClrProxy;)V"));
            return methods;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle createRobotProxy0(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0, global::net.sf.jni4net.utils.JniLocalHandle par1, global::net.sf.jni4net.utils.JniLocalHandle par2, global::net.sf.jni4net.utils.JniLocalHandle par3) {
            // (Lnet/sf/robocode/host/IHostManager;Lrobocode/control/RobotSpecification;Lnet/sf/robocode/peer/IRobotStatics;Lnet/sf/robocode/peer/IRobotPeer;)Lnet/sf/robocode/host/proxies/IHostingRobotProxy;
            // (Lnet/sf/robocode/host/IHostManager;Lrobocode/control/RobotSpecification;Lnet/sf/robocode/peer/IRobotStatics;Lnet/sf/robocode/peer/IRobotPeer;)Lnet/sf/robocode/host/proxies/IHostingRobotProxy;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            global::net.sf.robocode.dotnet.host.DotnetHost @__real = global::net.sf.jni4net.utils.Convertor.StrongJp2C<global::net.sf.robocode.dotnet.host.DotnetHost>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.FullC2J<global::net.sf.robocode.host.proxies.IHostingRobotProxy>(@__env, ((global::net.sf.robocode.host.IHost)(@__real)).createRobotProxy(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::net.sf.robocode.host.IHostManager>(@__env, par0), global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::robocode.control.RobotSpecification>(@__env, par1), global::net.sf.jni4net.utils.Convertor.FullJ2C<global::net.sf.robocode.peer.IRobotStatics>(@__env, par2), global::net.sf.jni4net.utils.Convertor.FullJ2C<global::net.sf.robocode.peer.IRobotPeer>(@__env, par3)));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle getReferencedClasses1(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0) {
            // (Lnet/sf/robocode/repository/IRobotRepositoryItem;)[Ljava/lang/String;
            // (Lnet/sf/robocode/repository/IRobotRepositoryItem;)[Ljava/lang/String;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            global::net.sf.robocode.dotnet.host.DotnetHost @__real = global::net.sf.jni4net.utils.Convertor.StrongJp2C<global::net.sf.robocode.dotnet.host.DotnetHost>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.ArrayStrongCp2J(@__env, ((global::net.sf.robocode.host.IHost)(@__real)).getReferencedClasses(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::net.sf.robocode.repository.IRobotRepositoryItem>(@__env, par0)));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle getRobotType2(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle par0, bool par1, bool par2) {
            // (Lnet/sf/robocode/repository/IRobotRepositoryItem;ZZ)Lnet/sf/robocode/repository/RobotType;
            // (Lnet/sf/robocode/repository/IRobotRepositoryItem;ZZ)Lnet/sf/robocode/repository/RobotType;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            global::net.sf.robocode.dotnet.host.DotnetHost @__real = global::net.sf.jni4net.utils.Convertor.StrongJp2C<global::net.sf.robocode.dotnet.host.DotnetHost>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.StrongCp2J(((global::net.sf.robocode.host.IHost)(@__real)).getRobotType(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::net.sf.robocode.repository.IRobotRepositoryItem>(@__env, par0), par1, par2));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle createRobotProxy3(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle hostManager, global::net.sf.jni4net.utils.JniLocalHandle robotSpecification, global::net.sf.jni4net.utils.JniLocalHandle statics, global::net.sf.jni4net.utils.JniLocalHandle peer) {
            // (Lnet/sf/robocode/host/IHostManager;Ljava/lang/Object;Lnet/sf/robocode/peer/IRobotStatics;Lnet/sf/robocode/peer/IRobotPeer;)Lnet/sf/robocode/host/proxies/IHostingRobotProxy;
            // (Lnet/sf/robocode/host/IHostManager;Ljava/lang/Object;Lnet/sf/robocode/peer/IRobotStatics;Lnet/sf/robocode/peer/IRobotPeer;)Lnet/sf/robocode/host/proxies/IHostingRobotProxy;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            global::net.sf.robocode.dotnet.host.DotnetHost @__real = global::net.sf.jni4net.utils.Convertor.StrongJp2C<global::net.sf.robocode.dotnet.host.DotnetHost>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.FullC2J<global::net.sf.robocode.host.proxies.IHostingRobotProxy>(@__env, @__real.createRobotProxy(global::net.sf.jni4net.utils.Convertor.FullJ2C<global::net.sf.robocode.host.IHostManager>(@__env, hostManager), global::net.sf.jni4net.utils.Convertor.FullJ2C<global::java.lang.Object>(@__env, robotSpecification), global::net.sf.jni4net.utils.Convertor.FullJ2C<global::net.sf.robocode.peer.IRobotStatics>(@__env, statics), global::net.sf.jni4net.utils.Convertor.FullJ2C<global::net.sf.robocode.peer.IRobotPeer>(@__env, peer)));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static void @__ctorDotnetHost0(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__class, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()V
            // ()V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            global::net.sf.robocode.dotnet.host.DotnetHost @__real = new global::net.sf.robocode.dotnet.host.DotnetHost();
            global::net.sf.jni4net.utils.Convertor.InitProxy(@__env, @__obj, @__real);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::net.sf.robocode.dotnet.host.@__DotnetHost(@__env);
            }
        }
    }
    #endregion
}
