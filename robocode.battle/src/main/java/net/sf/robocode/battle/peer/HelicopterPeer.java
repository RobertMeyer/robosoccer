package net.sf.robocode.battle.peer;

import java.awt.geom.Arc2D;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import net.sf.robocode.battle.BoundingRectangle;
import net.sf.robocode.battle.ItemDrop;
import net.sf.robocode.peer.BadBehavior;
import net.sf.robocode.peer.BulletStatus;
import net.sf.robocode.peer.DebugProperty;
import net.sf.robocode.peer.ExecCommands;
import net.sf.robocode.peer.ExecResults;
import net.sf.robocode.peer.IRobotPeer;
import robocode.EquipmentPart;
import robocode.EquipmentSlot;
import robocode.Event;
import robocode.control.RobotSpecification;
import robocode.control.snapshot.RobotState;

public class HelicopterPeer implements IRobotPeerBattle, IRobotPeer {
	private final RobotPeer r;
	
	public HelicopterPeer(RobotPeer peer) {
		this.r = peer;
		
	}

	/**
	 * @param s
	 * @see net.sf.robocode.battle.peer.RobotPeer#println(java.lang.String)
	 */
	public void println(String s) {
		r.println(s);
	}

	/**
	 * @param ex
	 * @see net.sf.robocode.battle.peer.RobotPeer#print(java.lang.Throwable)
	 */
	public void print(Throwable ex) {
		r.print(ex);
	}

	/**
	 * @param s
	 * @see net.sf.robocode.battle.peer.RobotPeer#print(java.lang.String)
	 */
	public void print(String s) {
		r.print(s);
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#readOutText()
	 */
	public String readOutText() {
		return r.readOutText();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRobotStatistics()
	 */
	public RobotStatistics getRobotStatistics() {
		return r.getRobotStatistics();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getStatistics()
	 */
	public ContestantStatistics getStatistics() {
		return r.getStatistics();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRobotSpecification()
	 */
	public RobotSpecification getRobotSpecification() {
		return r.getRobotSpecification();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isHouseRobot()
	 */
	public boolean isHouseRobot() {
		return r.isHouseRobot();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isJuniorRobot()
	 */
	public boolean isJuniorRobot() {
		return r.isJuniorRobot();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isInteractiveRobot()
	 */
	public boolean isInteractiveRobot() {
		return r.isInteractiveRobot();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isPaintRobot()
	 */
	public boolean isPaintRobot() {
		return r.isPaintRobot();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isTeamRobot()
	 */
	public boolean isTeamRobot() {
		return r.isTeamRobot();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getName()
	 */
	public String getName() {
		return r.getName();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getShortName()
	 */
	public String getShortName() {
		return r.getShortName();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getVeryShortName()
	 */
	public String getVeryShortName() {
		return r.getVeryShortName();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRobotIndex()
	 */
	public int getRobotIndex() {
		return r.getRobotIndex();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getTeamIndex()
	 */
	public int getTeamIndex() {
		return r.getTeamIndex();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getContestantIndex()
	 */
	public int getContestantIndex() {
		return r.getContestantIndex();
	}

	/**
	 * @param enabled
	 * @see net.sf.robocode.battle.peer.RobotPeer#setPaintEnabled(boolean)
	 */
	public void setPaintEnabled(boolean enabled) {
		r.setPaintEnabled(enabled);
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isPaintEnabled()
	 */
	public boolean isPaintEnabled() {
		return r.isPaintEnabled();
	}

	/**
	 * @param enabled
	 * @see net.sf.robocode.battle.peer.RobotPeer#setSGPaintEnabled(boolean)
	 */
	public void setSGPaintEnabled(boolean enabled) {
		r.setSGPaintEnabled(enabled);
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isSGPaintEnabled()
	 */
	public boolean isSGPaintEnabled() {
		return r.isSGPaintEnabled();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getState()
	 */
	public RobotState getState() {
		return r.getState();
	}

	/**
	 * @param state
	 * @see net.sf.robocode.battle.peer.RobotPeer#setState(robocode.control.snapshot.RobotState)
	 */
	public void setState(RobotState state) {
		r.setState(state);
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isWinner()
	 */
	public boolean isWinner() {
		return r.isWinner();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isRunning()
	 */
	public boolean isRunning() {
		return r.isRunning();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isSleeping()
	 */
	public boolean isSleeping() {
		return r.isSleeping();
	}

	/**
	 * @param value
	 * @see net.sf.robocode.battle.peer.RobotPeer#setHalt(boolean)
	 */
	public void setHalt(boolean value) {
		r.setHalt(value);
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getBoundingBox()
	 */
	public BoundingRectangle getBoundingBox() {
		return r.getBoundingBox();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getScanArc()
	 */
	public Arc2D getScanArc() {
		return r.getScanArc();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getGunHeading()
	 */
	public double getGunHeading() {
		return r.getGunHeading();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRadarHeading()
	 */
	public double getRadarHeading() {
		return r.getRadarHeading();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getVelocity()
	 */
	public double getVelocity() {
		return r.getVelocity();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getX()
	 */
	public double getX() {
		return r.getX();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getY()
	 */
	public double getY() {
		return r.getY();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getEnergy()
	 */
	public double getEnergy() {
		return r.getEnergy();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getGunHeat()
	 */
	public double getGunHeat() {
		return r.getGunHeat();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRadarColor()
	 */
	public int getRadarColor() {
		return r.getRadarColor();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getGunColor()
	 */
	public int getGunColor() {
		return r.getGunColor();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getBulletColor()
	 */
	public int getBulletColor() {
		return r.getBulletColor();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getScanColor()
	 */
	public int getScanColor() {
		return r.getScanColor();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getTeamPeer()
	 */
	public TeamPeer getTeamPeer() {
		return r.getTeamPeer();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getTeamName()
	 */
	public String getTeamName() {
		return r.getTeamName();
	}

	/**
	 * 
	 * @see net.sf.robocode.battle.peer.RobotPeer#checkSkippedTurn()
	 */
	public void checkSkippedTurn() {
		r.checkSkippedTurn();
	}

	/**
	 * @param otherRobot
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getNameForEvent(net.sf.robocode.battle.peer.RobotPeer)
	 */
	public String getNameForEvent(RobotPeer otherRobot) {
		return r.getNameForEvent(otherRobot);
	}

	/**
	 * @param event
	 * @see net.sf.robocode.battle.peer.RobotPeer#addEvent(robocode.Event)
	 */
	public void addEvent(Event event) {
		r.addEvent(event);
	}

	/**
	 * 
	 * @see net.sf.robocode.battle.peer.RobotPeer#drainEnergy()
	 */
	public void drainEnergy() {
		r.drainEnergy();
	}

	/**
	 * 
	 * @see net.sf.robocode.battle.peer.RobotPeer#cleanup()
	 */
	public void cleanup() {
		r.cleanup();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getGraphicsCalls()
	 */
	public Object getGraphicsCalls() {
		return r.getGraphicsCalls();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getDebugProperties()
	 */
	public List<DebugProperty> getDebugProperties() {
		return r.getDebugProperties();
	}

	/**
	 * @param bulletStatus
	 * @see net.sf.robocode.battle.peer.RobotPeer#addBulletStatus(net.sf.robocode.peer.BulletStatus)
	 */
	public void addBulletStatus(BulletStatus bulletStatus) {
		r.addBulletStatus(bulletStatus);
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getEquipment()
	 */
	public Collection<EquipmentPart> getEquipment() {
		return r.getEquipment();
	}

	/**
	 * @param bulletPower
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getBulletSpeed(double)
	 */
	public double getBulletSpeed(double bulletPower) {
		return r.getBulletSpeed(bulletPower);
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRobotAcceleration()
	 */
	public double getRobotAcceleration() {
		return r.getRobotAcceleration();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRobotDeceleration()
	 */
	public double getRobotDeceleration() {
		return r.getRobotDeceleration();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRadarScanRadius()
	 */
	public double getRadarScanRadius() {
		return r.getRadarScanRadius();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getGunTurnRate()
	 */
	public double getGunTurnRate() {
		return r.getGunTurnRate();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getGunTurnRateRadians()
	 */
	public double getGunTurnRateRadians() {
		return r.getGunTurnRateRadians();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getMaxTurnRate()
	 */
	public double getMaxTurnRate() {
		return r.getMaxTurnRate();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getMaxTurnRateRadians()
	 */
	public double getMaxTurnRateRadians() {
		return r.getMaxTurnRateRadians();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getMaxVelocity()
	 */
	public double getMaxVelocity() {
		return r.getMaxVelocity();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getStartingEnergy()
	 */
	public double getStartingEnergy() {
		return r.getStartingEnergy();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getEnergyRegen()
	 */
	public double getEnergyRegen() {
		return r.getEnergyRegen();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRobotArmor()
	 */
	public double getRobotArmor() {
		return r.getRobotArmor();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getMinBulletPower()
	 */
	public double getMinBulletPower() {
		return r.getMinBulletPower();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getMaxBulletPower()
	 */
	public double getMaxBulletPower() {
		return r.getMaxBulletPower();
	}

	/**
	 * @param bulletPower
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getGunHeat(double)
	 */
	public double getGunHeat(double bulletPower) {
		return r.getGunHeat(bulletPower);
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRamAttack()
	 */
	public double getRamAttack() {
		return r.getRamAttack();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRamDamage()
	 */
	public double getRamDamage() {
		return r.getRamDamage();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRadarTurnRate()
	 */
	public double getRadarTurnRate() {
		return r.getRadarTurnRate();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getRadarTurnRateRadians()
	 */
	public double getRadarTurnRateRadians() {
		return r.getRadarTurnRateRadians();
	}

	/**
	 * @param cp
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#compareTo(net.sf.robocode.battle.peer.ContestantPeer)
	 */
	public int compareTo(ContestantPeer cp) {
		return r.compareTo(cp);
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return r.equals(obj);
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getAnnonymousName()
	 */
	public String getAnnonymousName() {
		return r.getAnnonymousName();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getBodyHeading()
	 */
	public double getBodyHeading() {
		return r.getBodyHeading();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#getBodyColor()
	 */
	public int getBodyColor() {
		return r.getBodyColor();
	}

	/**
	 * @throws IOException
	 * @see net.sf.robocode.battle.peer.RobotPeer#executeImplSerial()
	 */
	public void executeImplSerial() throws IOException {
		r.executeImplSerial();
	}

	/**
	 * @param newCommands
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#executeImpl(net.sf.robocode.peer.ExecCommands)
	 */
	public final ExecResults executeImpl(ExecCommands newCommands) {
		return r.executeImpl(newCommands);
	}

	/**
	 * @param partName
	 * @see net.sf.robocode.battle.peer.RobotPeer#equip(java.lang.String)
	 */
	public void equip(String partName) {
		r.equip(partName);
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return r.hashCode();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isDroid()
	 */
	public boolean isDroid() {
		return r.isDroid();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isAdvancedRobot()
	 */
	public boolean isAdvancedRobot() {
		return r.isAdvancedRobot();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isDead()
	 */
	public boolean isDead() {
		return r.isDead();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isAlive()
	 */
	public boolean isAlive() {
		return r.isAlive();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isHalt()
	 */
	public boolean isHalt() {
		return r.isHalt();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isTeamLeader()
	 */
	public boolean isTeamLeader() {
		return r.isTeamLeader();
	}

	/**
	 * @param otherRobot
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isTeamMate(net.sf.robocode.battle.peer.RobotPeer)
	 */
	public boolean isTeamMate(RobotPeer otherRobot) {
		return r.isTeamMate(otherRobot);
	}

	/**
	 * @param bidirectionalBuffer
	 * @see net.sf.robocode.battle.peer.RobotPeer#setupBuffer(java.nio.ByteBuffer)
	 */
	public void setupBuffer(ByteBuffer bidirectionalBuffer) {
		r.setupBuffer(bidirectionalBuffer);
	}

	/**
	 * 
	 * @see net.sf.robocode.battle.peer.RobotPeer#setupThread()
	 */
	public void setupThread() {
		r.setupThread();
	}

	/**
	 * @throws IOException
	 * @see net.sf.robocode.battle.peer.RobotPeer#waitForBattleEndImplSerial()
	 */
	public void waitForBattleEndImplSerial() throws IOException {
		r.waitForBattleEndImplSerial();
	}

	/**
	 * @param newCommands
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#waitForBattleEndImpl(net.sf.robocode.peer.ExecCommands)
	 */
	public final ExecResults waitForBattleEndImpl(ExecCommands newCommands) {
		return r.waitForBattleEndImpl(newCommands);
	}

	/**
	 * 
	 * @see net.sf.robocode.battle.peer.RobotPeer#waitWakeup()
	 */
	public void waitWakeup() {
		r.waitWakeup();
	}

	/**
	 * 
	 * @see net.sf.robocode.battle.peer.RobotPeer#waitWakeupNoWait()
	 */
	public void waitWakeupNoWait() {
		r.waitWakeupNoWait();
	}

	/**
	 * @param millisWait
	 * @param nanosWait
	 * @see net.sf.robocode.battle.peer.RobotPeer#waitSleeping(long, int)
	 */
	public void waitSleeping(long millisWait, int nanosWait) {
		r.waitSleeping(millisWait, nanosWait);
	}

	/**
	 * @param robots
	 * @param initialRobotPositions
	 * @see net.sf.robocode.battle.peer.RobotPeer#initializeRound(java.util.List, double[][])
	 */
	public void initializeRound(List<RobotPeer> robots,
			double[][] initialRobotPositions) {
		r.initializeRound(robots, initialRobotPositions);
	}

	/**
	 * @param waitMillis
	 * @param waitNanos
	 * @see net.sf.robocode.battle.peer.RobotPeer#startRound(long, int)
	 */
	public void startRound(long waitMillis, int waitNanos) {
		r.startRound(waitMillis, waitNanos);
	}

	/**
	 * 
	 * @see net.sf.robocode.battle.peer.RobotPeer#performLoadCommands()
	 */
	public void performLoadCommands() {
		r.performLoadCommands();
	}

	/**
	 * @param robots
	 * @param items
	 * @param zapEnergy
	 * @see net.sf.robocode.battle.peer.RobotPeer#performMove(java.util.List, java.util.List, double)
	 */
	public final void performMove(List<RobotPeer> robots, List<ItemDrop> items,
			double zapEnergy) {
		r.performMove(robots, items, zapEnergy);
	}

	/**
	 * @param robots
	 * @see net.sf.robocode.battle.peer.RobotPeer#performScan(java.util.List)
	 */
	public void performScan(List<RobotPeer> robots) {
		r.performScan(robots);
	}

	/**
	 * 
	 * @see net.sf.robocode.battle.peer.RobotPeer#updateBoundingBox()
	 */
	public void updateBoundingBox() {
		r.updateBoundingBox();
	}

	/**
	 * @param value
	 * @see net.sf.robocode.battle.peer.RobotPeer#setRunning(boolean)
	 */
	public void setRunning(boolean value) {
		r.setRunning(value);
	}

	/**
	 * @param badBehavior
	 * @see net.sf.robocode.battle.peer.RobotPeer#punishBadBehavior(net.sf.robocode.peer.BadBehavior)
	 */
	public void punishBadBehavior(BadBehavior badBehavior) {
		r.punishBadBehavior(badBehavior);
	}

	/**
	 * @param delta
	 * @see net.sf.robocode.battle.peer.RobotPeer#updateEnergy(double)
	 */
	public void updateEnergy(double delta) {
		r.updateEnergy(delta);
	}

	/**
	 * @param newWinner
	 * @see net.sf.robocode.battle.peer.RobotPeer#setWinner(boolean)
	 */
	public void setWinner(boolean newWinner) {
		r.setWinner(newWinner);
	}

	/**
	 * 
	 * @see net.sf.robocode.battle.peer.RobotPeer#kill()
	 */
	public void kill() {
		r.kill();
	}

	/**
	 * 
	 * @see net.sf.robocode.battle.peer.RobotPeer#waitForStop()
	 */
	public void waitForStop() {
		r.waitForStop();
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#isTryingToPaint()
	 */
	public boolean isTryingToPaint() {
		return r.isTryingToPaint();
	}

	/**
	 * @param currentTurn
	 * @see net.sf.robocode.battle.peer.RobotPeer#publishStatus(long)
	 */
	public void publishStatus(long currentTurn) {
		r.publishStatus(currentTurn);
	}

	/**
	 * @param slot
	 * @see net.sf.robocode.battle.peer.RobotPeer#unequip(robocode.EquipmentSlot)
	 */
	public void unequip(EquipmentSlot slot) {
		r.unequip(slot);
	}

	/**
	 * @return
	 * @see net.sf.robocode.battle.peer.RobotPeer#toString()
	 */
	public String toString() {
		return r.toString();
	}
}
