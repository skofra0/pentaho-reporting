/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2001 - 2013 Object Refinery Ltd, Hitachi Vantara and Contributors..  All rights reserved.
 */

package org.pentaho.reporting.engine.classic.core.modules.parser.bundle.data;

import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.ParameterMapping;
import org.pentaho.reporting.engine.classic.core.ReportDataFactoryException;
import org.pentaho.reporting.engine.classic.core.function.Expression;

/**
 * A result object for the XML parser. It contains all parameter definitions as well as all the data-source definitions
 * and once the parsing finishs, it is merged with the current report definition.
 *
 * @author Thomas Morgner
 */
public class SubReportDataDefinition {
  private static final Expression[] EMPTY_EXPRESSIONS = new Expression[0];

  private ParameterMapping[] importParameters;
  private ParameterMapping[] exportParameters;
  private Expression[] expressions;
  private DataFactory primaryDataFactory;
  private String query;
  private int queryLimit;
  private int queryTimeout;

  public SubReportDataDefinition( final ParameterMapping[] importParameters, final ParameterMapping[] exportParameters,
      final DataFactory primaryDataFactory, final String query, final int queryLimit, final int queryTimeout,
      final Expression[] expressions ) {
    this.primaryDataFactory = primaryDataFactory;
    if ( exportParameters != null ) {
      this.exportParameters = (ParameterMapping[]) exportParameters.clone();
    }
    if ( importParameters != null ) {
      this.importParameters = (ParameterMapping[]) importParameters.clone();
    }
    this.query = query;
    this.queryLimit = queryLimit;
    this.queryTimeout = queryTimeout;
    this.expressions = (Expression[]) expressions.clone();
  }

  public String getQuery() {
    return query;
  }

  public int getQueryLimit() {
    return queryLimit;
  }

  public int getQueryTimeout() {
    return queryTimeout;
  }

  public Expression[] getExpressions() {
    if ( expressions == null ) {
      return EMPTY_EXPRESSIONS;
    }

    final Expression[] targetExpressions = new Expression[expressions.length];
    for ( int i = 0; i < expressions.length; i++ ) {
      final Expression expression = expressions[i];
      targetExpressions[i] = expression.getInstance();
    }
    return targetExpressions;
  }

  public ParameterMapping[] getExportParameters() {
    if ( exportParameters == null ) {
      return null;
    }
    return (ParameterMapping[]) exportParameters.clone();
  }

  public ParameterMapping[] getImportParameters() {
    if ( importParameters == null ) {
      return null;
    }
    return (ParameterMapping[]) importParameters.clone();
  }

  public DataFactory getDataFactory() throws ReportDataFactoryException {
    if ( primaryDataFactory != null ) {
      return primaryDataFactory.derive();
    }
    return null;
  }
}
