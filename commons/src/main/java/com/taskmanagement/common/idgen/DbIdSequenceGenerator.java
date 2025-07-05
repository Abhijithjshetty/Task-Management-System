package com.taskmanagement.common.idgen;

public class DbIdSequenceGenerator implements IdGen {

    private char[] allowedChars = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', // last index 11
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', // last index 23
            'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'  // last index 35
    };

    private SequenceGenerator<Long> sequence;

    public DbIdSequenceGenerator(SequenceGenerator<Long> sequence) {
        this.sequence = sequence;
    }

    @Override
    public String nextId() {
        return this.nextId(sequence.next());
    }

    @Override
    public String nextId(Long id) {
        int[] seq = new int[8];
        Long q;
        Long r;

        for(int i=7; i>=0; i--) {
            q = id / allowedChars.length;
            r = id % allowedChars.length;
            id = q;
            seq[i] = Math.toIntExact(r);
        }

        return buildString(seq);
    }

    private String buildString(int[] seq) {
        StringBuilder builder = new StringBuilder();
        for(int i: seq) {
            builder.append(allowedChars[i]);
        }
        return builder.toString();
    }
}
