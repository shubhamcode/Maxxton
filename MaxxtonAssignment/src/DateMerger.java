import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateMerger {
	
	
	public List<DateRange> mergeDates(List<DateRange> dateRanges) throws ParseException
	{
				
		int len=dateRanges.size()-2;
		int[] flag=new int[len+1];
		int x=0;
		
		List<DateRange> mergedDateRange=new ArrayList<>();
		
		
		if(len <0)
			return dateRanges;
		
		//calculating difference between each range and stroing result in temp array flag
		for (int i=0;i<=len;i++)
		{
			String endDate1=dateRanges.get(i).getEndDate();
			String startDate2=dateRanges.get(i+1).getStartDate();
			flag[x++]=checkNoGap(endDate1,startDate2);
			
			
		}
		
		int fromIndex=0;//starting index for a new range
		int tillIndex=0;//last index to be included in a new range
		
		//providing merged ranges by evaluation temp flag array
		for (int i=0;i<x;i++)
		{	
			
			DateRange drNew;
			
			
			
			if (flag[i]==1)
			{	
				drNew=new DateRange();
				tillIndex=i;
				String startDate2=dateRanges.get(fromIndex).getStartDate();
				String endDate1=dateRanges.get(tillIndex).getEndDate();
				fromIndex=tillIndex+1;
				
				drNew.setStartDate(startDate2);
				drNew.setEndDate(endDate1);
				mergedDateRange.add(drNew);
			}
			
			if (flag[i]==0 && i==x-1)
			{
				drNew=new DateRange();
				String startDate2=dateRanges.get(fromIndex).getStartDate();
				String endDate1=dateRanges.get(x).getEndDate();
				
				drNew.setStartDate(startDate2);
				drNew.setEndDate(endDate1);
				mergedDateRange.add(drNew);
			}
			
			if (flag[i]==1 && i==x-1)
			{
				drNew=new DateRange();
				String startDate2=dateRanges.get(fromIndex).getStartDate();
				String endDate1=dateRanges.get(x).getEndDate();
				
				drNew.setStartDate(startDate2);
				drNew.setEndDate(endDate1);
				
				mergedDateRange.add(drNew);
			}
			
		
		}
		
		return mergedDateRange;
	}
	
	//to calculate the diff between two dates and get it in no of days
	private int checkNoGap(String endDate, String startDate) throws ParseException {
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd MMM yyyy");
		Date enddt=sdf.parse(endDate);
		Date startdt=sdf.parse(startDate);
		
		long diff=((startdt.getTime()-enddt.getTime())/(1000*60*60*24))%365;
		
		if(diff <=0)
			return 0;
		
		
		return 1;
		
		
		
	}

	public static void main (String[] ss) throws ParseException
	{
		List<DateRange> dateRange=new ArrayList<>();
		
		
		//hardcoding the input date ranges
		DateRange d1=new DateRange();
		d1.setStartDate("01 Jan 2014");
		d1.setEndDate("15 Jan 2014");			
		dateRange.add(d1);
		
		
		d1=new DateRange();
		d1.setStartDate("15 Jan 2014");
		d1.setEndDate("30 Jan 2014");
		dateRange.add(d1);
		
		d1=new DateRange();
		d1.setStartDate("10 Mar 2014");
		d1.setEndDate("15 Apr 2014");		
		dateRange.add(d1);
		
		d1=new DateRange();
		d1.setStartDate("10 Apr 2014");
		d1.setEndDate("15 May 2014");	
		dateRange.add(d1); 
	
		
		
		
		//printing the original input date ranges
		System.out.println("Original Ranges");
		
		for (DateRange odt:dateRange)
			System.out.println(odt.startDate + " - " + odt.endDate);

		//processing and printing the merged date ranges
		
		DateMerger dm=new DateMerger();
		List<DateRange> newRange=dm.mergeDates(dateRange);
		
		System.out.println("New Merged Ranges");
		
		for (DateRange dt:newRange)
			System.out.println(dt.startDate + " - " + dt.endDate);
		
	}

}
