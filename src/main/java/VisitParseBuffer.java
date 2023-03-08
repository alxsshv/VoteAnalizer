public class VisitParseBuffer implements XMLParseBuffer {
    private StringBuilder builder;
    private int size;
    public VisitParseBuffer() {
        this.builder = new StringBuilder();
    }

    public void addRecord(String station, String visitDate, String visitTime){
        if (!builder.isEmpty()){
            builder.append(",");
        }
        builder.append("('");
        builder.append(station);
        builder.append("', '");
        builder.append(visitDate);
        builder.append("', '");
        builder.append(visitTime);
        builder.append("')");
        size++;
    }
    public void clear(){
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
