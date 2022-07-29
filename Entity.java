public abstract class Entity {
    
    private Group parentGroup;

    public Entity(Group parentGroup) {
        this.setParentGroup(parentGroup);
    }

    public void setParentGroup(Group parentGroup) {
        this.parentGroup = parentGroup;
    }

    public Group getParentGroup() {
        return this.parentGroup;
    }

}
