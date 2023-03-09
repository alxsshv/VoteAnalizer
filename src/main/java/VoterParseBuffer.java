public class VoterParseBuffer implements XMLParseBuffer {
    private StringBuilder builder;
    private int size;
    public VoterParseBuffer() {
        this.builder = new StringBuilder();
    }

    public void addRecord(String name, String birthDay){
        if (!builder.isEmpty()){
            builder.append(",");
        }
        builder.append("('");
        builder.append(name);
        builder.append("', '");
        builder.append(birthDay);
        builder.append("')");
        size++;
    }
    public void clear(){
        size = 0;
        builder = new StringBuilder();
    }

    public boolean isEmpty(){
        return builder.isEmpty();
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString(){
        return builder.toString();
    }

}
