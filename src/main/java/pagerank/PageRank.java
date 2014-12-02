package pagerank;

import java.util.ArrayList;

import crawler.Website;

public class PageRank {
    public ArrayList<Website> websiteList;
    private static final double terminationDelta = 0.001;
    private double teleportationPropability = 0.1;

    public PageRank(ArrayList<Website> websiteList) {
        this.websiteList = websiteList;
    }
    public void calculatePagranks() {
        System.out.println("Start PageRank Calculation");
        this.setInitialPagerank();
        int iterationCounter = 1;
        while(!this.hasTerminatingAccuracy())
        {
            this.printPageRankSum();
            this.iteratePagerank();
            System.out.println("PageRank iteration " + iterationCounter);
            iterationCounter++;
        }
        System.out.println("PageRank successfull calculated");
        this.printPageRankResults();
    }

    private void printPageRankResults() {
          for ( Website website : this.websiteList )
          {
     
              System.out.println("#Site:" + website.getUrl() + " #PageRank: " + website.pageRankNew);
          }   

    }
    private boolean hasTerminatingAccuracy() {
        double absSumChangesWithLastIteration = 0;
          for ( Website website : this.websiteList )
          {
              absSumChangesWithLastIteration += Math.abs(website.pageRankNew - website.pageRankOld);
          }
        if(absSumChangesWithLastIteration < terminationDelta)
        {
            return true;
        }
        return false;
    }
    private void setInitialPagerank() {
      double initialPagerank = 1.0 / this.websiteList.size();
      for ( Website website : this.websiteList )
      {
          website.pageRankNew = initialPagerank;
      }
    }

    private void iteratePagerank() {
          for ( Website website : this.websiteList )
          {
              website.pageRankOld = website.pageRankNew;
              website.pageRankNew = 0.0;
          }
          for ( Website website : this.websiteList )
          {
              double x = this.getTeleportPagerankValue(website);
              double y = this.getPagesPagerankValue(website);
              website.pageRankNew = x + y;
          }
    }
    private double getTeleportPagerankValue(Website website) {
        return (this.teleportationPropability/this.getAmountwebsite());
    }

    private double getPagesPagerankValue(Website website) {
        double linkedPagerankValue = this.getLinkedPagerankValue(website);
        double nonLinkedPagesPagerankValue = this.getNonLinkedPagesPagerankValue(website);
        return (1.0 - this.teleportationPropability) * (linkedPagerankValue + nonLinkedPagesPagerankValue);
    }

    private double getLinkedPagerankValue(Website website) {
      double linkedPagerankValue = 0;
      for ( Website otherwebsite : this.websiteList )
      {
          if(otherwebsite.isLinkedWithPage(website))
          {
              linkedPagerankValue += otherwebsite.pageRankOld / (double)otherwebsite.numberOfOutgoingLinks();
          }
      }
      //System.out.println("linkedPagerankValue: " + linkedPagerankValue);
      return linkedPagerankValue;
    }    

    private double getNonLinkedPagesPagerankValue(Website website) {
          double nonLinkedPagerankValue = 0;
          for ( Website otherwebsite : this.websiteList )
          {
              if(otherwebsite.hasNoOutgoingLinks())
              {
                  nonLinkedPagerankValue += otherwebsite.pageRankOld / this.getAmountwebsite();
              }
          }
          //System.out.println("nonLinkedPagerankValue: " + nonLinkedPagerankValue);
        return nonLinkedPagerankValue;
    }
    private int getAmountwebsite() {
        return this.websiteList.size();
    }
    private void printPageRankSum() {
        double sumPagerank = 0;
          for ( Website otherwebsite : this.websiteList )
          {
              sumPagerank += otherwebsite.pageRankNew;
          }
        System.out.println("Summe Pagerank:" + sumPagerank);
    }
}