package view.component.group;

import view.component.Bound;

public abstract class GroupComponent {

    Bound bound;

    public GroupComponent(Bound bound) {
        this.bound = bound;
    }

    public void setBound(Bound bound) {
        this.bound = bound;
    }

    public Bound getBound() {
        return bound;
    }
}
