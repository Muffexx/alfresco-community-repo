/*
 * #%L
 * Alfresco Data model classes
 * %%
 * Copyright (C) 2005 - 2016 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software.
 * If the software was purchased under a paid Alfresco license, the terms of
 * the paid license agreement will prevail.  Otherwise, the software is
 * provided under the following open source license terms:
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
// CHECKSTYLE:OFF
/**
 * Source code generated by Fluent Builders Generator
 * Do not modify this file
 * See generator home page at: http://code.google.com/p/fluent-builders-generator-eclipse-plugin/
 */

package org.alfresco.repo.index.shard;

import java.util.HashMap;
import java.util.HashSet;
import org.alfresco.service.cmr.repository.StoreRef;

public class ShardStateBuilder
  extends ShardStateBuilderBase<ShardStateBuilder> {

  public static ShardStateBuilder shardState() {
    return new ShardStateBuilder();
  }

  public ShardStateBuilder() {
    super(new ShardState());
  }

  public ShardState build() {
    return getInstance();
  }
}

class ShardStateBuilderBase<GeneratorT extends ShardStateBuilderBase<GeneratorT>> {

  private ShardState instance;

  protected ShardStateBuilderBase(ShardState aInstance) {
    instance = aInstance;
  }

  protected ShardState getInstance() {
    return instance;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withShardInstance(ShardInstance aValue) {
    instance.setShardInstance(aValue);

    return (GeneratorT) this;
  }

  public ShardInstanceShardInstanceBuilder withShardInstance() {
    ShardInstance obj = new ShardInstance();

    withShardInstance(obj);

    return new ShardInstanceShardInstanceBuilder(obj);
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withMaster(boolean aValue) {
    instance.setMaster(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withPropertyBag(HashMap<String, String> aValue) {
    instance.setPropertyBag(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withLastUpdated(long aValue) {
    instance.setLastUpdated(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withLastIndexedChangeSetId(long aValue) {
    instance.setLastIndexedChangeSetId(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withLastIndexedTxCommitTime(long aValue) {
    instance.setLastIndexedTxCommitTime(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withLastIndexedTxId(long aValue) {
    instance.setLastIndexedTxId(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withLastIndexedChangeSetCommitTime(long aValue) {
    instance.setLastIndexedChangeSetCommitTime(aValue);

    return (GeneratorT) this;
  }

  public class ShardInstanceShardInstanceBuilder
    extends ShardInstanceBuilderBase<ShardInstanceShardInstanceBuilder> {

    public ShardInstanceShardInstanceBuilder(ShardInstance aInstance) {
      super(aInstance);
    }

    @SuppressWarnings("unchecked")
    public GeneratorT endShardInstance() {
      return (GeneratorT) ShardStateBuilderBase.this;
    }
  }

  public static class ShardInstanceBuilderBase<GeneratorT extends ShardInstanceBuilderBase<GeneratorT>> {

    private ShardInstance instance;

    protected ShardInstanceBuilderBase(ShardInstance aInstance) {
      instance = aInstance;
    }

    protected ShardInstance getInstance() {
      return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withShard(Shard aValue) {
      instance.setShard(aValue);

      return (GeneratorT) this;
    }

    public ShardShardBuilder withShard() {
      Shard obj = new Shard();

      withShard(obj);

      return new ShardShardBuilder(obj);
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withBaseUrl(String aValue) {
      instance.setBaseUrl(aValue);

      return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withPort(int aValue) {
      instance.setPort(aValue);

      return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withHostName(String aValue) {
      instance.setHostName(aValue);

      return (GeneratorT) this;
    }

    public class ShardShardBuilder extends ShardBuilderBase<ShardShardBuilder> {

      public ShardShardBuilder(Shard aInstance) {
        super(aInstance);
      }

      @SuppressWarnings("unchecked")
      public GeneratorT endShard() {
        return (GeneratorT) ShardInstanceBuilderBase.this;
      }
    }
  }

  public static class ShardBuilderBase<GeneratorT extends ShardBuilderBase<GeneratorT>> {

    private Shard instance;

    protected ShardBuilderBase(Shard aInstance) {
      instance = aInstance;
    }

    protected Shard getInstance() {
      return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withFloc(Floc aValue) {
      instance.setFloc(aValue);

      return (GeneratorT) this;
    }

    public FlocFlocBuilder withFloc() {
      Floc obj = new Floc();

      withFloc(obj);

      return new FlocFlocBuilder(obj);
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withInstance(int aValue) {
      instance.setInstance(aValue);

      return (GeneratorT) this;
    }

    public class FlocFlocBuilder extends FlocBuilderBase<FlocFlocBuilder> {

      public FlocFlocBuilder(Floc aInstance) {
        super(aInstance);
      }

      @SuppressWarnings("unchecked")
      public GeneratorT endFloc() {
        return (GeneratorT) ShardBuilderBase.this;
      }
    }
  }

  public static class FlocBuilderBase<GeneratorT extends FlocBuilderBase<GeneratorT>> {

    private Floc instance;

    protected FlocBuilderBase(Floc aInstance) {
      instance = aInstance;
    }

    protected Floc getInstance() {
      return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withStoreRefs(HashSet<StoreRef> aValue) {
      instance.setStoreRefs(aValue);

      return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withAddedStoreRef(StoreRef aValue) {
      if (instance.getStoreRefs() == null) {
        instance.setStoreRefs(new HashSet<StoreRef>());
      }

      ((HashSet<StoreRef>) instance.getStoreRefs()).add(aValue);

      return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withNumberOfShards(int aValue) {
      instance.setNumberOfShards(aValue);

      return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withShardMethod(ShardMethodEnum aValue) {
      instance.setShardMethod(aValue);

      return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withTemplate(String aValue) {
      instance.setTemplate(aValue);

      return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withHasContent(boolean aValue) {
      instance.setHasContent(aValue);

      return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withPropertyBag(HashMap<String, String> aValue) {
      instance.setPropertyBag(aValue);

      return (GeneratorT) this;
    }
  }
}
