/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2010 EchoSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.php.cpd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.sonar.plugins.php.cpd.PhpCpdConfiguration.PHPCPD_DEFAULT_REPORT_FILE_NAME;
import static org.sonar.plugins.php.cpd.PhpCpdConfiguration.PHPCPD_DEFAULT_REPORT_FILE_PATH;
import static org.sonar.plugins.php.cpd.PhpCpdConfiguration.PHPCPD_REPORT_FILE_NAME_PROPERTY_KEY;
import static org.sonar.plugins.php.cpd.PhpCpdConfiguration.PHPCPD_REPORT_FILE_RELATIVE_PATH_PROPERTY_KEY;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.configuration.Configuration;
import org.apache.maven.project.MavenProject;
import org.junit.Test;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.ProjectFileSystem;

/**
 * The Class PhpCpdConfigurationTest.
 */
public class PhpCpdConfigurationTest {

  /**
   * Should get valid suffixe option.
   */
  @Test
  public void shouldReturnDefaultReportFileWithDefaultPath() {
    Project project = mock(Project.class);
    Configuration configuration = getMockConfiguration(project);
    when(configuration.getString(PHPCPD_REPORT_FILE_RELATIVE_PATH_PROPERTY_KEY, PHPCPD_DEFAULT_REPORT_FILE_PATH)).thenReturn(
        PHPCPD_DEFAULT_REPORT_FILE_PATH);
    when(project.getConfiguration()).thenReturn(configuration);
    PhpCpdConfiguration config = new PhpCpdConfiguration(project);
    assertEquals(config.getReportFile().getPath().replace('/', '\\'), "C:\\projets\\PHP\\Monkey\\target\\logs\\php-cpd.xml");
  }

  /**
   * Should get valid suffixe option.
   */
  @Test
  public void shouldReturnDefaultReportFileWithCustomPath() {
    Project project = mock(Project.class);
    Configuration configuration = getMockConfiguration(project);

    when(configuration.getString(PHPCPD_REPORT_FILE_RELATIVE_PATH_PROPERTY_KEY, PHPCPD_DEFAULT_REPORT_FILE_PATH)).thenReturn("reports");
    when(project.getConfiguration()).thenReturn(configuration);
    PhpCpdConfiguration config = new PhpCpdConfiguration(project);
    assertEquals(config.getReportFile().getPath().replace('/', '\\'), "C:\\projets\\PHP\\Monkey\\target\\reports\\php-cpd.xml");
  }

  /**
   * Should return custom report file with custom path.
   */
  @Test
  public void shouldReturnCustomReportFileWithCustomPath() {
    Project project = mock(Project.class);
    Configuration configuration = getMockConfiguration(project);

    String customReportFileName = "php-cpd-my-custom-file.xml";
    when(configuration.getString(PHPCPD_REPORT_FILE_NAME_PROPERTY_KEY, PHPCPD_DEFAULT_REPORT_FILE_NAME)).thenReturn(customReportFileName);
    when(configuration.getString(PHPCPD_REPORT_FILE_RELATIVE_PATH_PROPERTY_KEY, PHPCPD_DEFAULT_REPORT_FILE_PATH)).thenReturn("reports");
    when(project.getConfiguration()).thenReturn(configuration);
    PhpCpdConfiguration config = new PhpCpdConfiguration(project);
    assertEquals(config.getReportFile().getPath().replace('/', '\\'), "C:\\projets\\PHP\\Monkey\\target\\reports\\" + customReportFileName);
  }

  /**
   * @param project
   * @return
   */
  private Configuration getMockConfiguration(Project project) {
    Configuration configuration = mock(Configuration.class);
    MavenProject mavenProject = mock(MavenProject.class);
    ProjectFileSystem fs = mock(ProjectFileSystem.class);
    when(project.getPom()).thenReturn(mavenProject);
    when(project.getFileSystem()).thenReturn(fs);
    when(fs.getSourceDirs()).thenReturn(Arrays.asList(new File("C:\\projets\\PHP\\Monkey\\sources\\main")));
    when(fs.getTestDirs()).thenReturn(Arrays.asList(new File("C:\\projets\\PHP\\Monkey\\Sources\\test")));
    when(fs.getBuildDir()).thenReturn(new File("C:\\projets\\PHP\\Monkey\\target"));
    when(configuration.getString(PHPCPD_REPORT_FILE_NAME_PROPERTY_KEY, PHPCPD_DEFAULT_REPORT_FILE_NAME)).thenReturn(
        PHPCPD_DEFAULT_REPORT_FILE_NAME);
    return configuration;
  }
}