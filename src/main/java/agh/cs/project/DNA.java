package agh.cs.project;

import java.util.*;

public class DNA
{
    private final int length = 32;
    private ArrayList<Integer> chain = new ArrayList<Integer>();

    public DNA()
    {
        Random r = new Random();
        for(int i=0;i<length;i++)
        {
            chain.add(r.nextInt(8));

        }
        chain=fixDNA(chain);
    }

    public DNA(Animal dad, Animal mom)
    {
        Random r = new Random();
        int index = r.nextInt(length-2)+1;
        int index2 = r.nextInt(length-2)+1;

        while(index2 >= index-1 && index2 <= index+1)
        {
            index2 = r.nextInt(length-2)+1;
        }
        if(index2<index)
        {
            int tmp = index;
            index=index2;
            index2 = tmp;
        }

        ArrayList<Integer> first_draft = new ArrayList<Integer>();
        int i =0;
        int count_dad=0;
        int count_mom=0;
        int choose = r.nextInt(2);
        Animal chosen;
        if(choose==0)
        {
            chosen = dad;
            count_dad+=1;
        }
        else
        {
            chosen = mom;
            count_mom+=1;
        }
        for(i =0; i<index; i++)//first block
        {
            first_draft.add(chosen.getDnaDigit(i));
        }

        choose = r.nextInt(2);
        if(choose==0)
        {
            chosen = dad;
            count_dad+=1;
        }
        else
        {
            chosen = mom;
            count_mom+=1;
        }
        for(;i<index2;i++)//second block
        {
            first_draft.add(chosen.getDnaDigit(i));
        }

        if(count_dad == 2)
        {
            chosen = mom;
        }else {
            if (count_mom == 2) {
                chosen = dad;
            } else {
                choose = r.nextInt(2);
                if (choose == 0) {
                    chosen = mom;
                } else {
                    chosen = dad;
                }
            }
        }
        for(;i<length;i++)//second block
        {
            first_draft.add(chosen.getDnaDigit(i));
        }
        chain = fixDNA(first_draft);

    }

    private ArrayList<Integer> fixDNA(ArrayList<Integer> toFix)
    {
        Random r = new Random();
        ArrayList<Integer> count = new ArrayList<Integer>();
        for(int i =0; i<8;i++)
        {
            count.add(0);
        }
        for(int i = 0; i<length;i++)
        {
            int p = toFix.get(i);
            int val = count.get(p)+1;
            count.set(p,val);
        }
        while(count.contains(0))
        {

            int index0 = count.indexOf(0);
            //System.out.println(index0);
            int change_index =r.nextInt(count.size());
            while(count.get(change_index)==1 &&count.get(change_index)==0)
            {
                change_index = r.nextInt(count.size());
            }
            count.set(change_index, count.get(change_index)-1);
            count.set(index0, count.get(index0)+1);
        }
        int a =0;
        for(int i =0 ; i< count.size() ; i++)
        {
            for(int j =0; j<count.get(i);j++)
            {
                if(a<32)
                {
                    toFix.set(a,i);
                    a++;
                }

            }
        }
        return toFix;

    }
    
    public int getRotationNumber()
    {
        Random r = new Random();
        return chain.get(r.nextInt(chain.size()));
    }

    public int get(int i)
    {
        return chain.get(i);
    }

    @Override
    public String toString() {
        return "DNA{" +
                  chain +
                '}';
    }
}
