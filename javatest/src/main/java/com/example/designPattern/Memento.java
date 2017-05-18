package com.example.designPattern;

/**
 * Created by YanYadi on 2017/5/16.
 * 备忘录模式，用于记录事件状态，恢复至某一个状态。
 */
public class Memento {
    public static void main(String[] args) {
        PlayManager playManager = new PlayManager();
        // 创建一个玩家，100 的血量 、 600 的攻击力
        PlayOriginator playOriginator = new PlayOriginator(100, 600);
        playOriginator.viewStatus();
        System.out.println(" 30 minute a go. ");
        System.out.println("backup player properties.");
        playManager.pushMemento(playOriginator.backup());
        System.out.println("weapon wear..   aggressivity subtract 200 ");
        playOriginator.aggressivity -= 200;
        playOriginator.viewStatus();
        // 时光倒流，回退上一个版本
        System.out.println("turn back in time ");
        playOriginator.updateProperties(playManager.popMemento());
        playOriginator.viewStatus();

    }
    /**
     * 定义 存储实体
     */
    static class MementoEntity {
        /**
         * 生命值
         */
        int vitality;
        /**
         * 攻击力
         */
        int aggressivity;
    }

    /**
     * 定义玩家
     */
    static class PlayOriginator {

        private int vitality;
        private int aggressivity;

        PlayOriginator(int vitality, int aggressivity) {
            this.vitality = vitality;
            this.aggressivity = aggressivity;
        }

        /**
         * 从指定的 备份数据中，更新数据
         *
         * @param memento
         */
        public void updateProperties(MementoEntity memento) {
            vitality = memento.vitality;
            aggressivity = memento.aggressivity;
        }

        public void viewStatus() {
            System.out.println("monster:\n\t vitality : " + vitality + "\n\t aggressivity : " + aggressivity);
        }

        public MementoEntity backup() {
            MementoEntity mementoEntity = new MementoEntity();
            mementoEntity.vitality = vitality;
            mementoEntity.aggressivity = aggressivity;
            return mementoEntity;
        }
    }

    /**
     * 定义游戏管理者，负责 存储、读取玩家数据
     */
    static class PlayManager {
        MementoEntity[] backups = new MementoEntity[10];
        private int sIndex;

        public void pushMemento(MementoEntity entity) {
            if (sIndex == backups.length)
                throw new IndexOutOfBoundsException("Memento array is full .");
            backups[sIndex++] = entity;
        }

        public MementoEntity popMemento() {
            if (sIndex > 0){
                MementoEntity mementoEntity = backups[--sIndex];
                backups[sIndex] = null;
                return  mementoEntity;
            }

            System.out.println("backups is empty!");
            return null;
        }


    }


}
