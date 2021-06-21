package com.robertx22.library_of_exile.registry;

import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExileDatapackGenerator<T extends IGUID & ISerializable<T>> extends BaseDatapackGenerator<T> {
    String modid;

    public ExileDatapackGenerator(String modid, List<T> list, String category) {
        super(list, category);
        this.modid = modid;
    }

    @Override
    public void run() {
        generateAll(cache);
    }

    public Path resolve(Path path, T object) {
        return path.resolve(
            "data/" + modid + "/" + category + "/" + object.datapackFolder() + object.getFileName()
                .replaceAll(":", "_") +
                ".json");
    }

    protected void generateAll(DataCache cache) {

        try {
            Path path = gameDirPath();

            for (T entry : list) {

                if (!entry.shouldGenerateJson()) {
                    continue;
                }

                Path target = movePath(resolve(path, entry));

                target = Paths.get(target.toString()
                    .replace("\\.\\", "\\"));

                try {
                    DataProvider.writeToPath(GSON, cache, entry.toJson(), target);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
}
