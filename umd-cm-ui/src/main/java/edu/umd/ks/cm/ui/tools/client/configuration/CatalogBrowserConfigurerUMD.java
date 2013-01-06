package edu.umd.ks.cm.ui.tools.client.configuration;

import org.kuali.student.lum.lu.ui.tools.client.configuration.CatalogBrowserConfigurer;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CatalogBrowserController;

public class CatalogBrowserConfigurerUMD extends CatalogBrowserConfigurer {

    @Override
    public void configureCatalogBrowser(CatalogBrowserController layout) {
        layout.addStyleName("browseCatalog");
        layout.setBasicTitle("Browse Course Catalog");
        layout.addTab(createBrowseBySubjectAreaSection(),
                "Browse By Subject Area");
        //layout.addTab(createBrowseBySchoolSection(), "Browse By School");
        layout.setDefaultView(Sections.BROWSE_BY_SUBJECT_AREA);
    }

}
