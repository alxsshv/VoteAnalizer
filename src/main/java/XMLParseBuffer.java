public class XMLParseBuffer {
    private StringBuilder builder;
    private int recordsCount;
    public XMLParseBuffer() {
        this.builder = new StringBuilder();
    }

    public void addVoter(String name, String birthDay){
        if (builder.isEmpty()){
            builder.append(",");
        }
        builder.append("('");
        builder.append(name);
        builder.append("', '");
        builder.append(birthDay);
        builder.append("', 1)");
        recordsCount++;
    }

    public String getBufferString(){
        return builder.toString();
    }

    public void clear(){
        builder = new StringBuilder();
    }
    public int getRecordsCount() {
        return recordsCount;
    }


}
