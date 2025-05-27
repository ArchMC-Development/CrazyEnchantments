package com.badbones69.crazyenchantments.paper.support;

import com.badbones69.crazyenchantments.paper.support.interfaces.claims.ClaimSupport;
import net.william278.husktowns.api.HuskTownsAPI;
import net.william278.husktowns.claim.Position;
import net.william278.husktowns.claim.World;
import net.william278.husktowns.libraries.cloplib.operation.*;
import net.william278.husktowns.town.Town;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HuskTownsSupport implements ClaimSupport {

    @Override
    public boolean isFriendly(@NotNull Player player, Player other) {
        var userTown = HuskTownsAPI.getInstance().getUserTown(
            HuskTownsAPI.getInstance().getOnlineUser(player.getUniqueId())
        );

        var otherTown = HuskTownsAPI.getInstance().getUserTown(
            HuskTownsAPI.getInstance().getOnlineUser(other.getUniqueId())
        );

        return otherTown.isPresent() && userTown.isPresent() &&
            otherTown.get().town() != null &&
            userTown.get().town() != null &&
            otherTown.get().town().areRelationsBilateral(
                otherTown.get().town(), Town.Relation.ALLY
            );
    }

    public boolean inTerritory(Player player) {
        HuskTownsAPI ht = HuskTownsAPI.getInstance();
        var userTown = HuskTownsAPI.getInstance().getUserTown(
            HuskTownsAPI.getInstance().getOnlineUser(player.getUniqueId())
        );
        var claim = ht.getClaimAt(Position.at(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), World.of(
            player.getLocation().getWorld().getUID(),
            player.getLocation().getWorld().getName(),
            player.getLocation().getWorld().getEnvironment().name()
        )));

        return claim.isPresent() && userTown.isPresent() && userTown.get().town() != null && userTown.get().town().getId() == claim.get().town().getId();

    }

    public boolean canBreakBlock(Player player, Block block) {
        var location = block.getLocation();
        return HuskTownsAPI.getInstance().isOperationAllowed(Operation.of(
            OperationType.BLOCK_BREAK,
            new OperationPosition() {
                @Override
                public double getX() {
                    return location.getX();
                }

                @Override
                public double getY() {
                    return location.getY();
                }

                @Override
                public double getZ() {
                    return location.getZ();
                }

                @Override
                public @net.william278.husktowns.libraries.annotations.NotNull OperationWorld getWorld() {
                    return new OperationWorld() {
                        @Override
                        public @net.william278.husktowns.libraries.annotations.NotNull String getName() {
                            return location.getWorld().getName();
                        }

                        @Override
                        public @net.william278.husktowns.libraries.annotations.NotNull UUID getUuid() {
                            return location.getWorld().getUID();
                        }
                    };
                }

                @Override
                public @net.william278.husktowns.libraries.annotations.NotNull OperationChunk getChunk() {
                    return new OperationChunk() {
                        @Override
                        public int getX() {
                            return location.getChunk().getX();
                        }

                        @Override
                        public int getZ() {
                            return location.getChunk().getZ();
                        }
                    };
                }
            }
        ));
    }

    public static boolean allowsCombat(Location location) {
        return HuskTownsAPI.getInstance().isOperationAllowed(Operation.of(
            OperationType.PLAYER_DAMAGE_PLAYER,
            new OperationPosition() {
                @Override
                public double getX() {
                    return location.getX();
                }

                @Override
                public double getY() {
                    return location.getY();
                }

                @Override
                public double getZ() {
                    return location.getZ();
                }

                @Override
                public @net.william278.husktowns.libraries.annotations.NotNull OperationWorld getWorld() {
                    return new OperationWorld() {
                        @Override
                        public @net.william278.husktowns.libraries.annotations.NotNull String getName() {
                            return location.getWorld().getName();
                        }

                        @Override
                        public @net.william278.husktowns.libraries.annotations.NotNull UUID getUuid() {
                            return location.getWorld().getUID();
                        }
                    };
                }

                @Override
                public @net.william278.husktowns.libraries.annotations.NotNull OperationChunk getChunk() {
                    return new OperationChunk() {
                        @Override
                        public int getX() {
                            return location.getChunk().getX();
                        }

                        @Override
                        public int getZ() {
                            return location.getChunk().getZ();
                        }
                    };
                }
            }
        ));
        /*TownyAPI api = TownyAPI.getInstance();

        if (api == null) return true;

        TownBlock block = api.getTownBlock(location);

        return block == null || !CombatUtil.preventPvP(block.getWorld(), block);*/
    }
}
