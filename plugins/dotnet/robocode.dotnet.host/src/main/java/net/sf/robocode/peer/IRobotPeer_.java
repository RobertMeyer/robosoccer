// ------------------------------------------------------------------------------
//  <autogenerated>
//      This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
// 
//      Changes to this file may cause incorrect behavior and will be lost if 
//      the code is regenerated.
//  </autogenerated>
// ------------------------------------------------------------------------------

package net.sf.robocode.peer;

@net.sf.jni4net.attributes.ClrTypeInfo
public final class IRobotPeer_ {
    
    //<generated-static>
    private static system.Type staticType;
    
    public static system.Type typeof() {
        return net.sf.robocode.peer.IRobotPeer_.staticType;
    }
    
    private static void InitJNI(net.sf.jni4net.inj.INJEnv env, system.Type staticType) {
        net.sf.robocode.peer.IRobotPeer_.staticType = staticType;
    }
    //</generated-static>
}

//<generated-proxy>
@net.sf.jni4net.attributes.ClrProxy
class __IRobotPeer extends system.Object implements net.sf.robocode.peer.IRobotPeer {
    
    protected __IRobotPeer(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("()V")
    public native void drainEnergy();
    
    @net.sf.jni4net.attributes.ClrMethod("(Lnet/sf/robocode/peer/BadBehavior;)V")
    public native void punishBadBehavior(net.sf.robocode.peer.BadBehavior par0);
    
    @net.sf.jni4net.attributes.ClrMethod("(Z)V")
    public native void setRunning(boolean par0);
    
    @net.sf.jni4net.attributes.ClrMethod("()Z")
    public native boolean isRunning();
    
    @net.sf.jni4net.attributes.ClrMethod("(Ljava/lang/Object;)Ljava/lang/Object;")
    public native net.sf.robocode.peer.ExecResults waitForBattleEndImpl(net.sf.robocode.peer.ExecCommands par0);
    
    @net.sf.jni4net.attributes.ClrMethod("(Ljava/lang/Object;)Ljava/lang/Object;")
    public native net.sf.robocode.peer.ExecResults executeImpl(net.sf.robocode.peer.ExecCommands par0);
    
    @net.sf.jni4net.attributes.ClrMethod("(Ljava/nio/ByteBuffer;)V")
    public native void setupBuffer(java.nio.ByteBuffer par0);
    
    @net.sf.jni4net.attributes.ClrMethod("()V")
    public native void executeImplSerial();
    
    @net.sf.jni4net.attributes.ClrMethod("()V")
    public native void waitForBattleEndImplSerial();
    
    @net.sf.jni4net.attributes.ClrMethod("()V")
    public native void setupThread();
}
//</generated-proxy>
