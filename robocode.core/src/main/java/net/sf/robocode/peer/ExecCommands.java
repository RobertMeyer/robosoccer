/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package net.sf.robocode.peer;

import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.MinionProxy;
import robocode.Rules;
import robocode.robotinterfaces.peer.IBasicRobotPeer;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savara (original)
 * @author CSSE2003 Team forkbomb (contributor)
 */
public final class ExecCommands implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int defaultBodyColor = 0xFF29298C;
    public static final int defaultGunColor = 0xFF29298C;
    public static final int defaultRadarColor = 0xFF29298C;
    public static final int defaultScanColor = 0xFF0000FF;
    public static final int defaultBulletColor = 0xFFFFFFFF;
    public static final int defaultLandmineColor=0xFFFFFFFF;
    private double bodyTurnRemaining;
    private double radarTurnRemaining;
    private double gunTurnRemaining;
    private double distanceRemaining;
    private boolean isAdjustGunForBodyTurn;
    private boolean isAdjustRadarForGunTurn;
    private boolean isAdjustRadarForBodyTurn;
    private boolean isAdjustRadarForBodyTurnSet;
    private int bodyColor = defaultBodyColor;
    private int gunColor = defaultGunColor;
    private int radarColor = defaultRadarColor;
    private int scanColor = defaultScanColor;
    private int bulletColor = defaultBulletColor;

    private int deathEffect = 1;

    private int landmineColor=defaultLandmineColor;
    private double maxTurnRate;
    private double maxVelocity;
    private boolean moved;
    private boolean scan;
    private boolean isIORobot;
    private boolean isTryingToPaint;
    private boolean spawnMinion;
    private int minionEnergy;
    private int minionType;
    private String outputText;
    private List<BulletCommand> bullets = new ArrayList<BulletCommand>();
    private List<LandmineCommand> landmines= new ArrayList<LandmineCommand>();
    private List<TeamMessage> teamMessages = new ArrayList<TeamMessage>();
    private List<DebugProperty> debugProperties = new ArrayList<DebugProperty>();
    private List<MinionProxy> minions = new ArrayList<MinionProxy>();
    private MinionProxy parent;
    private Object graphicsCalls;
    
    private boolean melt; //indicator of robots wish to melt if frozen
    
    public ExecCommands() {
        setMaxVelocity(Double.MAX_VALUE);
        setMaxTurnRate(Double.MAX_VALUE);
    }

    public ExecCommands(ExecCommands origin, boolean fromRobot) {
        bodyTurnRemaining = origin.bodyTurnRemaining;
        radarTurnRemaining = origin.radarTurnRemaining;
        gunTurnRemaining = origin.gunTurnRemaining;
        distanceRemaining = origin.distanceRemaining;
        isAdjustGunForBodyTurn = origin.isAdjustGunForBodyTurn;
        isAdjustRadarForGunTurn = origin.isAdjustRadarForGunTurn;
        isAdjustRadarForBodyTurn = origin.isAdjustRadarForBodyTurn;
        isAdjustRadarForBodyTurnSet = origin.isAdjustRadarForBodyTurnSet;
        maxTurnRate = origin.maxTurnRate;
        maxVelocity = origin.maxVelocity;
        copyColors(origin);
        deathEffect = origin.deathEffect;
        minions = origin.minions;
        parent = origin.parent;
        if (fromRobot) {
            debugProperties = origin.debugProperties;
            bullets = origin.bullets;
            landmines=origin.landmines;
            scan = origin.scan;
            moved = origin.moved;
            graphicsCalls = origin.graphicsCalls;
            outputText = origin.outputText;
            teamMessages = origin.teamMessages;
            isTryingToPaint = origin.isTryingToPaint;
            spawnMinion = origin.spawnMinion;
            minionType = origin.minionType;
            minionEnergy = origin.minionEnergy;
            melt = origin.melt;
        }
    }

    public void copyColors(ExecCommands origin) {
        if (origin != null) {
            bodyColor = origin.bodyColor;
            gunColor = origin.gunColor;
            radarColor = origin.radarColor;
            bulletColor = origin.bulletColor;
            scanColor = origin.scanColor;
            landmineColor=origin.landmineColor;
        }
    }

    public int getBodyColor() {
        return bodyColor;
    }

    public int getRadarColor() {
        return radarColor;
    }

    public int getGunColor() {
        return gunColor;
    }

    public int getBulletColor() {
        return bulletColor;
    }
    
    public int getLandmineColor()
    {
    	return landmineColor;
    }

    public int getScanColor() {
        return scanColor;
    }
    
    public List<MinionProxy> getMinions() {
    	return minions;
    }
    
    public MinionProxy getParent() {
    	return parent;
    }
    
    public void setParent(MinionProxy parent) {
    	this.parent = parent;
    }
    
    public void setMinions(List<MinionProxy> minionProxyList) {
    	this.minions = minionProxyList;
    }
    
    public boolean getSpawnMinion() {
    	return spawnMinion;
    }
    
    public int getMinionEnergyCost() {
    	return minionEnergy;
    }
    
    public int getMinionType() {
    	return minionType;
    }
    
    public void setSpawnMinion(boolean spawnMinion, int minionType, int minionEnergy) {
    	this.minionType = minionType;
    	this.spawnMinion = spawnMinion;
    	this.minionEnergy = minionEnergy;
    }
        
    public int getDeathEffect() {
    	return deathEffect;
    }

    public void setBodyColor(int color) {
        bodyColor = color;
    }

    public void setRadarColor(int color) {
        radarColor = color;
    }

    public void setGunColor(int color) {
        gunColor = color;
    }

    public void setBulletColor(int color) {
        bulletColor = color;
    }
    
    public void setLandmineColor(int color)
    {
    	landmineColor=color;
    }

    public void setScanColor(int color) {
        scanColor = color;
    }

    public void setDeathEffect(int effect) {
    	deathEffect = effect;
    }
    
    public double getBodyTurnRemaining() {
        return bodyTurnRemaining;
    }

    public void setBodyTurnRemaining(double bodyTurnRemaining) {
        this.bodyTurnRemaining = bodyTurnRemaining;
    }

    public double getRadarTurnRemaining() {
        return radarTurnRemaining;
    }

    public void setRadarTurnRemaining(double radarTurnRemaining) {
        this.radarTurnRemaining = radarTurnRemaining;
    }

    public double getGunTurnRemaining() {
        return gunTurnRemaining;
    }

    public void setGunTurnRemaining(double gunTurnRemaining) {
        this.gunTurnRemaining = gunTurnRemaining;
    }

    public double getDistanceRemaining() {
        return distanceRemaining;
    }

    public void setDistanceRemaining(double distanceRemaining) {
        this.distanceRemaining = distanceRemaining;
    }

    public boolean isAdjustGunForBodyTurn() {
        return isAdjustGunForBodyTurn;
    }

    public void setAdjustGunForBodyTurn(boolean adjustGunForBodyTurn) {
        isAdjustGunForBodyTurn = adjustGunForBodyTurn;
    }

    public boolean isAdjustRadarForGunTurn() {
        return isAdjustRadarForGunTurn;
    }

    public void setAdjustRadarForGunTurn(boolean adjustRadarForGunTurn) {
        isAdjustRadarForGunTurn = adjustRadarForGunTurn;
    }

    public boolean isAdjustRadarForBodyTurn() {
        return isAdjustRadarForBodyTurn;
    }

    public void setAdjustRadarForBodyTurn(boolean adjustRadarForBodyTurn) {
        isAdjustRadarForBodyTurn = adjustRadarForBodyTurn;
    }

    public boolean isAdjustRadarForBodyTurnSet() {
        return isAdjustRadarForBodyTurnSet;
    }

    public void setAdjustRadarForBodyTurnSet(boolean adjustRadarForBodyTurnSet) {
        isAdjustRadarForBodyTurnSet = adjustRadarForBodyTurnSet;
    }

    public double getMaxTurnRate() {
        return maxTurnRate;
    }

    public void setMaxTurnRate(double maxTurnRate) {
        if (maxTurnRate == Double.MAX_VALUE) {
            this.maxTurnRate = Rules.MAX_TURN_RATE_RADIANS;
        } else {
            this.maxTurnRate = maxTurnRate;
        }
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        if (maxVelocity == Double.MAX_VALUE) {
            this.maxVelocity = Rules.MAX_VELOCITY;
        } else {
            this.maxVelocity = maxVelocity;
        }
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isScan() {
        return scan;
    }

    public void setScan(boolean scan) {
        this.scan = scan;
    }
    
    /**
     * Boolean indicating whether or not the robot wishes to melt if frozen
     * at the sacrifice of 30% of it's health
     * @return true or false indicating robots intentions
     */
    public boolean isMelt() {
    	return melt;
    }
    
    /**
     * Change the status of variable boolean melt
     * @param melt boolean indicating whether robot wishes to melt
     */
    public void setMelt(boolean melt) {
    	this.melt = melt;
    }

    public List<BulletCommand> getBullets() {
        return bullets;
    }
    
    public List<LandmineCommand> getLandmines() {
    	return landmines;
    }

    public Object getGraphicsCalls() {
        return graphicsCalls;
    }

    public List<DebugProperty> getDebugProperties() {
        return debugProperties;
    }

    public void setGraphicsCalls(Object graphicsCalls) {
        this.graphicsCalls = graphicsCalls;
    }

    public String getOutputText() {
        final String out = outputText;

        outputText = "";
        if (out == null) {
            return "";
        }
        return out;
    }

    public void setOutputText(String out) {
        outputText = out;
    }

    public List<TeamMessage> getTeamMessages() {
        return teamMessages;
    }

    public boolean isIORobot() {
        return isIORobot;
    }

    public void setIORobot() {
        isIORobot = true;
    }

    public void setDebugProperty(String key, String value) {
        debugProperties.add(new DebugProperty(key, value));
    }

    public boolean isTryingToPaint() {
        return isTryingToPaint;
    }

    public void setTryingToPaint(boolean tryingToPaint) {
        isTryingToPaint = tryingToPaint;
    }

    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    private static class SerializableHelper implements ISerializableHelper {

        @Override
        public int sizeOf(RbSerializer serializer, Object object) {
            ExecCommands obj = (ExecCommands) object;
            int size = RbSerializer.SIZEOF_TYPEINFO + 4 * RbSerializer.SIZEOF_DOUBLE;

            size += 4 * RbSerializer.SIZEOF_BOOL;
            size += 6 * RbSerializer.SIZEOF_INT;//number of item colors
            size += 2 * RbSerializer.SIZEOF_DOUBLE;
            size += 6 * RbSerializer.SIZEOF_BOOL;
            size += serializer.sizeOf(obj.outputText);

            size += serializer.sizeOf((byte[]) obj.graphicsCalls);

            // bullets
            size += obj.bullets.size() * serializer.sizeOf(RbSerializer.BulletCommand_TYPE, null);
            size += 1;
            
            // landmines
            size += obj.landmines.size() * serializer.sizeOf(RbSerializer.LandmineCommand_TYPE, null);
            size += 1;

            // messages
            for (TeamMessage m : obj.teamMessages) {
                size += serializer.sizeOf(RbSerializer.TeamMessage_TYPE, m);
            }
            size += 1;

            // properties
            for (DebugProperty d : obj.debugProperties) {
                size += serializer.sizeOf(RbSerializer.DebugProperty_TYPE, d);
            }
            size += 1;

            return size;
        }

        @Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
            ExecCommands obj = (ExecCommands) object;

            serializer.serialize(buffer, obj.bodyTurnRemaining);
            serializer.serialize(buffer, obj.radarTurnRemaining);
            serializer.serialize(buffer, obj.gunTurnRemaining);
            serializer.serialize(buffer, obj.distanceRemaining);

            serializer.serialize(buffer, obj.isAdjustGunForBodyTurn);
            serializer.serialize(buffer, obj.isAdjustRadarForGunTurn);
            serializer.serialize(buffer, obj.isAdjustRadarForBodyTurn);
            serializer.serialize(buffer, obj.isAdjustRadarForBodyTurnSet);

            serializer.serialize(buffer, obj.bodyColor);
            serializer.serialize(buffer, obj.gunColor);
            serializer.serialize(buffer, obj.radarColor);
            serializer.serialize(buffer, obj.scanColor);
            serializer.serialize(buffer, obj.bulletColor);
            serializer.serialize(buffer, obj.landmineColor);

            serializer.serialize(buffer, obj.maxTurnRate);
            serializer.serialize(buffer, obj.maxVelocity);

            serializer.serialize(buffer, obj.moved);
            serializer.serialize(buffer, obj.scan);
            serializer.serialize(buffer, obj.isIORobot);
            serializer.serialize(buffer, obj.isTryingToPaint);
            serializer.serialize(buffer, obj.spawnMinion);
            serializer.serialize(buffer, obj.melt);

            serializer.serialize(buffer, obj.outputText);

            serializer.serialize(buffer, (byte[]) obj.graphicsCalls);

            for (BulletCommand bullet : obj.bullets) {
                serializer.serialize(buffer, RbSerializer.BulletCommand_TYPE, bullet);
            }
            buffer.put(RbSerializer.TERMINATOR_TYPE);
            for (LandmineCommand landmine : obj.landmines) {
                serializer.serialize(buffer, RbSerializer.LandmineCommand_TYPE, landmine);
            }
            buffer.put(RbSerializer.TERMINATOR_TYPE);
            for (TeamMessage message : obj.teamMessages) {
                serializer.serialize(buffer, RbSerializer.TeamMessage_TYPE, message);
            }
            buffer.put(RbSerializer.TERMINATOR_TYPE);
            for (DebugProperty prop : obj.debugProperties) {
                serializer.serialize(buffer, RbSerializer.DebugProperty_TYPE, prop);
            }
            buffer.put(RbSerializer.TERMINATOR_TYPE);
        }

        @Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            ExecCommands res = new ExecCommands();

            res.bodyTurnRemaining = buffer.getDouble();
            res.radarTurnRemaining = buffer.getDouble();
            res.gunTurnRemaining = buffer.getDouble();
            res.distanceRemaining = buffer.getDouble();

            res.isAdjustGunForBodyTurn = serializer.deserializeBoolean(buffer);
            res.isAdjustRadarForGunTurn = serializer.deserializeBoolean(buffer);
            res.isAdjustRadarForBodyTurn = serializer.deserializeBoolean(buffer);
            res.isAdjustRadarForBodyTurnSet = serializer.deserializeBoolean(buffer);

            res.bodyColor = buffer.getInt();
            res.gunColor = buffer.getInt();
            res.radarColor = buffer.getInt();
            res.scanColor = buffer.getInt();
            res.bulletColor = buffer.getInt();
            res.landmineColor=buffer.getInt();

            res.maxTurnRate = buffer.getDouble();
            res.maxVelocity = buffer.getDouble();

            res.moved = serializer.deserializeBoolean(buffer);
            res.scan = serializer.deserializeBoolean(buffer);
            res.isIORobot = serializer.deserializeBoolean(buffer);
            res.isTryingToPaint = serializer.deserializeBoolean(buffer);
            res.spawnMinion = serializer.deserializeBoolean(buffer);
            res.melt = serializer.deserializeBoolean(buffer);

            res.outputText = serializer.deserializeString(buffer);

            res.graphicsCalls = serializer.deserializeBytes(buffer);

            Object item = serializer.deserializeAny(buffer);

            while (item != null) {
                if (item instanceof BulletCommand) {
                    res.bullets.add((BulletCommand) item);
                }
                item = serializer.deserializeAny(buffer);
            }
            item = serializer.deserializeAny(buffer);
            while (item != null) {
                if (item instanceof LandmineCommand) {
                    res.landmines.add((LandmineCommand) item);
                }
                item = serializer.deserializeAny(buffer);
            }
            item = serializer.deserializeAny(buffer);
            while (item != null) {
                if (item instanceof TeamMessage) {
                    res.teamMessages.add((TeamMessage) item);
                }
                item = serializer.deserializeAny(buffer);
            }
            item = serializer.deserializeAny(buffer);
            while (item != null) {
                if (item instanceof DebugProperty) {
                    res.debugProperties.add((DebugProperty) item);
                }
                item = serializer.deserializeAny(buffer);
            }
            return res;
        }
    }
}
