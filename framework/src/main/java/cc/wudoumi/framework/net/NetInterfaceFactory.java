package cc.wudoumi.framework.net;


import cc.wudoumi.framework.net.ion.IonNetInterface;

/**
 * @author Qianjujun
 * @time 2016/3/8
 */
public class NetInterfaceFactory {
    public static NetInterface getNetInterface(){
        return IonNetInterface.get();
    }
}
